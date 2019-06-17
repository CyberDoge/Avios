package io.ssau.team.Avios.model;

import io.ssau.team.Avios.model.json.ThemeJson;

import java.util.LinkedList;
import java.util.List;

public class Theme {

    private Integer id;

    private String title;

    private String text;

    //проголосовавшие за и против
    private List<Integer> votedYes;
    private List<Integer> votedNo;
    private Boolean end;


    public Theme(Integer id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        votedYes = new LinkedList<>();
        votedNo = new LinkedList<>();
        end = false;
    }

    public Theme() {
        votedYes = new LinkedList<>();
        votedNo = new LinkedList<>();
    }

    public Theme(ThemeJson themeJson) {
        this.title = themeJson.title;
        this.text = themeJson.text;
        votedYes = new LinkedList<>();
        votedNo = new LinkedList<>();
    }

    public Boolean getEnd() {
        return end;
    }

    public void setEnd(Boolean end) {
        this.end = end;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
