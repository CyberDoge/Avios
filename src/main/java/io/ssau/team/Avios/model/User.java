package io.ssau.team.Avios.model;

import java.util.LinkedList;
import java.util.List;

public class User {
    private Integer id;
    private String username;

    //темы которые выбрал юзер
    private List<Integer> voteYesThemes;
    private List<Integer> voteNoThemes;

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
        voteYesThemes = new LinkedList<>();
        voteNoThemes = new LinkedList<>();
    }

    public List<Integer> getVoteYesThemes() {
        return voteYesThemes;
    }

    public List<Integer> getVoteNoThemes() {
        return voteNoThemes;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

}
