package io.ssau.team.Avios.socketController;

import io.ssau.team.Avios.socketModel.Chat;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class ChatController {
    private LinkedList<Chat> chatList;

    public ChatController() {
        chatList = new LinkedList<>();
    }

    public void addChat(Chat chat) {
        chatList.push(chat);
        chat.startChat();
    }
}
