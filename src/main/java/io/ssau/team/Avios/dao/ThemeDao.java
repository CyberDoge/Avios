package io.ssau.team.Avios.dao;

import io.ssau.team.Avios.model.Theme;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ThemeDao {
    private final List<Theme> themes = new ArrayList<>();

    @PostConstruct
    public void init() {
        themes.add(new Theme(1, "name1", "Comment1"));
        themes.add(new Theme(2, "name2", "Comment2"));
        themes.add(new Theme(3, "name3", "Comment3"));
    }

    public Theme create(Theme theme) {
        theme.setId(themes.size()+1);
        themes.add(theme);
        return theme;
    }


    public Theme getById(int id) {
        return themes.get(id);
    }


    public ArrayList<Theme> getThemesFrom(int index) {
        ArrayList<Theme> list = new ArrayList<>();
        if (index == 0) {
            themes.add(getById(themes.size() - 1));
        } else if (index == 1) {
            if (themes.size() < 10) {
                list.addAll(themes);
            } else {
                for (int i = themes.size() - 1; i > themes.size() - 11; i--) {
                    list.add(themes.get(i));
                }
            }
        }
        return list;
    }

    public void subscribeUserToTheme(Integer id, Integer userId, boolean agree) {
        if (agree) {
            themes.get(id).getVotedYes().add(userId);
        }else {
            themes.get(id).getVotedNo().add(userId);
        }
    }
}
