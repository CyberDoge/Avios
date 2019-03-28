package io.ssau.team.Avios.model.json;

import io.ssau.team.Avios.model.Theme;

public class ThemeJson {
    public Integer id;
    public String name;
    public String comment;

    //Колличество людей проголосовавших за или против
    public Integer votedYes;
    public Integer votedNo;

    public ThemeJson() {
    }

    public ThemeJson(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public ThemeJson(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.comment = theme.getComment();
        this.votedYes = theme.getVotedYes().size();
        this.votedNo = theme.getVotedNo().size();
    }
}
