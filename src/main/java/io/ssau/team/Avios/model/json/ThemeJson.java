package io.ssau.team.Avios.model.json;

import io.ssau.team.Avios.model.Theme;

public class ThemeJson {
    public Integer id;
    public String title;
    public String text;

    public Boolean voted;

    //Колличество людей проголосовавших за или против
    public Integer votedYes;
    public Integer votedNo;

    public ThemeJson() {
    }

    public ThemeJson(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public ThemeJson(Theme theme) {
        this.id = theme.getId();
        this.title = theme.getTitle();
        this.text = theme.getText();
        this.votedYes = theme.getVotedYes().size();
        this.votedNo = theme.getVotedNo().size();
    }

    public ThemeJson(Theme theme, Integer userId) {
        this.id = theme.getId();
        this.title = theme.getTitle();
        this.text = theme.getText();
        this.votedYes = theme.getVotedYes().size();
        this.votedNo = theme.getVotedNo().size();
        if (theme.getVotedNo().contains(userId)) {
            voted = false;
        } else if (theme.getVotedYes().contains(userId)) {
            voted = true;
        } else {
            voted = null;
        }
    }
}
