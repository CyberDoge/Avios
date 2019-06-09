package io.ssau.team.Avios.socketModel.json;

import io.ssau.team.Avios.socketModel.Chat;

import java.util.List;

public class ChatJson {
    public Integer id;
    public Integer firstUserId;
    public Integer secondUserId;
    public List<MessageJson> messages;

    public ChatJson(Chat chat) {
        this.id = chat.getChatId();
        this.firstUserId = chat.getFirstUser().getId();
        this.secondUserId = chat.getSecondUser().getId();
        this.messages = chat
    }
}
