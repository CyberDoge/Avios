package io.ssau.team.Avios.dao;

import io.ssau.team.Avios.model.Theme;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
        theme.setId(themes.size() + 1);
        themes.add(theme);
        return theme;
    }

    public void deleteById(Integer themeId) {
        themes.removeIf(theme -> Objects.equals(themeId, theme.getId()));
    }


    public Theme getById(Integer id) {
        return themes.stream().filter(theme -> Objects.equals(theme.getId(), id)).findFirst().orElse(null);
    }

    public List<Theme> getAll() {
        return themes;
    }

    public void deleteUserFromQueue(Integer firstUserId, Integer secondUserId, Integer themeId) {
        getById(themeId).getVotedNo().removeIf(i -> Objects.equals(firstUserId, i) || Objects.equals(secondUserId, i));
        getById(themeId).getVotedYes().removeIf(i -> Objects.equals(firstUserId, i) || Objects.equals(secondUserId, i));
    }

    public List<Theme> getThemesFrom(int index) {
        ArrayList<Theme> listTheme = new ArrayList<>(10);
        int from = index * 10;

        //выщитываем нужное колличество и заполняем
        for (int i = themes.size() - 1 - from, n = i - 10; i > n && i >= 0; i--) {
            listTheme.add(themes.get(i));
        }
        return listTheme;
    }

    public void subscribeUserToTheme(Integer id, Integer userId, boolean agree) {
        Theme theme = getById(id);
        if (agree) {
            if (theme.getVotedYes().remove(userId)) return;
            theme.getVotedYes().add(userId);
            theme.getVotedNo().remove(userId);
        } else {
            if (theme.getVotedNo().remove(userId)) return;
            theme.getVotedNo().add(userId);
            theme.getVotedYes().remove(userId);
        }
    }
}
