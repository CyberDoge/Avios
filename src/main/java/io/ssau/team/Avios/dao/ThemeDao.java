package io.ssau.team.Avios.dao;

import io.ssau.team.Avios.model.Theme;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    public void deleteById(Integer themeId) {
        themes.removeIf(theme -> Objects.equals(themeId, theme.getId()));
    }


    public Theme getById(int id) {
        return themes.stream().filter(theme -> Objects.equals(theme.getId(), id)).findFirst().get();
    }

    public List<Theme> getAll() {
        return themes;
    }


    public LinkedList<Theme> getThemesFrom(int index) {
        //todo баг
        LinkedList<Theme> listTheme = new LinkedList<>();
        if (index == 0) {
            listTheme.add(themes.get(themes.size() - 1));
        } else if (index == 1) {
            if (themes.size() < 10) {
                listTheme.addAll(themes);
            } else {
                for (int i = themes.size() - 1; i > themes.size() - 11; i--) {
                    listTheme.push(themes.get(i));
                }
            }
        }
        return listTheme;
    }

    public void subscribeUserToTheme(Integer id, Integer userId, boolean agree) {
        if (agree) {
            themes.get(id).getVotedYes().add(userId);
        }else {
            themes.get(id).getVotedNo().add(userId);
        }
    }
}
