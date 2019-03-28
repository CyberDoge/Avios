package io.ssau.team.Avios.model;

import java.util.LinkedList;
import java.util.List;

public class User {
    private Integer id;
    private String username;

    //темы которые выбрал юзер
    private List<Integer> voteUpThemes;
    private List<Integer> voteDownThemes;

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
        voteUpThemes = new LinkedList<>();
        voteDownThemes = new LinkedList<>();
    }

    public List<Integer> getVoteUpThemes() {
        return voteUpThemes;
    }

    public List<Integer> getVoteDownThemes() {
        return voteDownThemes;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

}
