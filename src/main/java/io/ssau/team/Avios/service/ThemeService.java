package io.ssau.team.Avios.service;

import io.ssau.team.Avios.dao.ThemeDao;
import io.ssau.team.Avios.dao.UserDao;
import io.ssau.team.Avios.model.Theme;
import io.ssau.team.Avios.model.User;
import io.ssau.team.Avios.model.json.ThemeJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private ThemeDao themeDao;
    private UserDao userDao;

    @Autowired
    public ThemeService(ThemeDao themeDao, UserDao userDao) {
        this.themeDao = themeDao;
        this.userDao = userDao;
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

    //todo разбораться когда удалять
    public ThemeJson getReadyTheme(Integer userId) {
        User user = userDao.getUserById(userId);
        for (Integer voteDown : user.getVoteDownThemes()) {
            Theme theme = themeDao.getById(voteDown);
            List<Integer> votedYes = theme.getVotedYes();
            if (votedYes.size() != 0) {

            }
        }
        for (Integer voteUp : user.getVoteUpThemes()) {

        }
        return null;

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
