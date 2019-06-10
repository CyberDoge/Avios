package io.ssau.team.Avios.socketModel.db_model;

import io.ssau.team.Avios.socketModel.Chat;

public class ChatDb {
    public Integer id;
    public Integer themeId;
    public Integer firstUserId;
    public Integer secondUserId;

    public ChatDb() {
    }

    public ChatDb(Chat chat) {
        this.id = chat.getChatId();
        this.themeId = chat.getThemeId();
        this.firstUserId = chat.getFirstUser().getId();
        this.secondUserId = chat.getSecondUser().getId();
    }
}
