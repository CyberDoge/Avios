package io.ssau.team.Avios.service;

import io.ssau.team.Avios.dao.RoomDao;
import io.ssau.team.Avios.dao.ThemeDao;
import io.ssau.team.Avios.dao.UserDao;
import io.ssau.team.Avios.model.Room;
import io.ssau.team.Avios.model.Theme;
import io.ssau.team.Avios.model.json.ThemeJson;
import io.ssau.team.Avios.socketService.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private ThemeDao themeDao;
    private RoomDao roomDao;
    private UserDao userDao;
    private ChatService chatService;

    @Autowired
    public ThemeService(ThemeDao themeDao, RoomDao roomDao, UserDao userDao, ChatService chatService) {
        this.themeDao = themeDao;
        this.roomDao = roomDao;
        this.userDao = userDao;
        this.chatService = chatService;
    }

    @Scheduled(fixedDelay = Integer.MAX_VALUE)
    public void onlyForTest() {
        //subscribeToTheme(2, 4, true);
        //subscribeToTheme(2, 5, false);
    }

    @Scheduled(fixedDelay = 5000)
    public void createRoom() {
        final List<Room> readyRooms = this.themeDao.getAll().parallelStream()
                .filter(theme -> !theme.getVotedYes().isEmpty() && !theme.getVotedNo().isEmpty())
                .map(theme -> {
                    final Integer votedYesUserId = theme.getVotedYes().get(theme.getVotedYes().size() - 1);
                    final Integer votedNoUserId = theme.getVotedNo().get(theme.getVotedNo().size() - 1);
                    final Integer id = theme.getId();
                    return new Room(id, votedYesUserId, votedNoUserId);
                }).collect(Collectors.toList());
        readyRooms.forEach(room -> {
            //удаляем все остальные ожидающие темы у юзера
            themeDao.deleteUserFromQueue(room.getVotedNoUserId(), room.getVotedYesUserId(), room.getThemeId());
            userDao.deleteThemesQueue(room.getVotedNoUserId());
            userDao.deleteThemesQueue(room.getVotedYesUserId());
            //создаем чат
            chatService.createChat(room.getVotedNoUserId(), room.getVotedYesUserId(), room.getThemeId());
        });
        roomDao.addAll(readyRooms);
    }

    public ThemeJson createTheme(ThemeJson themeJson) {
        Theme theme = themeDao.create(new Theme(themeJson));
        if (theme == null) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
        return new ThemeJson(theme);
    }

    public List<ThemeJson> getThemeList(int index, Integer userId) {
        List<Theme> themesFrom = themeDao.getThemesFrom(index);
        if(themesFrom.isEmpty()){
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
        }
        return themesFrom.stream().map(theme -> new ThemeJson(theme, userId)).collect(Collectors.toList());
    }

    public ThemeJson getReadyTheme(Integer userId) {
        return roomDao.getByUserId(userId)
                .map(room -> new ThemeJson(themeDao.getById(room.getThemeId()))).orElse(null);
    }

    public boolean subscribeToTheme(Integer id, Integer userId, boolean agree) {
        if (themeDao.getById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        themeDao.subscribeUserToTheme(id, userId, agree);
        userDao.subscribeToTheme(id, userId, agree);
        return true;
    }

    public ThemeJson getTheme(Integer id) {
        return new ThemeJson(themeDao.getById(id));
    }
}
