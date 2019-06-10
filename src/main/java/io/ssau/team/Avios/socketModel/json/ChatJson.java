package io.ssau.team.Avios.socketModel.json;

import io.ssau.team.Avios.socketModel.Chat;
import io.ssau.team.Avios.socketModel.db_model.ChatDb;

import java.util.List;

public class ChatJson {
    public Integer id;
    public Integer themeId;
    public Integer firstUserId;
    public Integer secondUserId;
    public List<MessageJson> messages;

    public ChatJson(Chat chat) {
        this.id = chat.getChatId();
        this.firstUserId = chat.getFirstUser().getId();
        this.secondUserId = chat.getSecondUser().getId();
        this.messages = chat.getMessages();
    }

    public ChatJson(ChatDb chatDb) {
        this.id = chatDb.id;
        this.themeId = chatDb.themeId;
        this.firstUserId = chatDb.firstUserId;
        this.secondUserId = chatDb.secondUserId;
    }
}
