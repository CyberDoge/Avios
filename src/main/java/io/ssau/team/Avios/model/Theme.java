package io.ssau.team.Avios.model;

import io.ssau.team.Avios.model.json.ThemeJson;

import java.util.LinkedList;
import java.util.List;

public class Theme {

    private Integer id;

    private String name;

    private String comment;

    //проголосовавшие за и против
    private List<Integer> votedYes;
    private List<Integer> votedNo;


    public Theme(Integer id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        votedYes = new LinkedList<>();
        votedNo = new LinkedList<>();
    }

    public Theme() {
        votedYes = new LinkedList<>();
        votedNo = new LinkedList<>();
    }

    public Theme(ThemeJson themeJson) {
        this.name = themeJson.name;
        this.comment = themeJson.comment;
        votedYes = new LinkedList<>();
        votedNo = new LinkedList<>();
    }

    public List<Integer> getVotedYes() {
        return votedYes;
    }

    public List<Integer> getVotedNo() {
        return votedNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
