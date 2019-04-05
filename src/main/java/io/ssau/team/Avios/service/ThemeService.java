package io.ssau.team.Avios.service;

import io.ssau.team.Avios.dao.RoomDao;
import io.ssau.team.Avios.dao.ThemeDao;
import io.ssau.team.Avios.dao.UserDao;
import io.ssau.team.Avios.model.Theme;
import io.ssau.team.Avios.model.json.ThemeJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private ThemeDao themeDao;
    private RoomDao roomDao;
    private UserDao userDao;

    @Autowired
    public ThemeService(ThemeDao themeDao, RoomDao roomDao, UserDao userDao) {
        this.themeDao = themeDao;
        this.roomDao = roomDao;
        this.userDao = userDao;
    }

    @Scheduled(fixedDelay = 5000)
    public void createRoom() {

    }

    public ThemeJson createTheme(ThemeJson themeJson) {
        Theme theme = themeDao.create(new Theme(themeJson));
        if (theme == null) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
        return new ThemeJson(theme);
    }

    public List<ThemeJson> getThemeList(int index) {
        return themeDao.getThemesFrom(index).stream().map(ThemeJson::new).collect(Collectors.toList());
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
}
