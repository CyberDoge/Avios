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
        LinkedList<Theme> listTheme = new LinkedList<>();
        int from = index * 10;
        if (themes.size() < from) {
            //если номер больше чем количество тем - возвращаем пустой
            return listTheme;
        }
        //выщитываем нужное колличество и заполняем
        for (int size = themes.size() > from + 10 ? from + 10 : themes.size(); from < size; from++) {
            listTheme.add(themes.get(from));
        }

        return listTheme;
    }

    public void subscribeUserToTheme(Integer id, Integer userId, boolean agree) {
        if (agree) {
            getById(id).getVotedYes().add(userId);
        } else {
            getById(id).getVotedNo().add(userId);
        }
    }
}
