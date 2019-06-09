package io.ssau.team.Avios.socketService;

import io.ssau.team.Avios.dao.ChatDao;
import io.ssau.team.Avios.socketModel.Chat;
import io.ssau.team.Avios.socketModel.json.ChatJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatService {


    private ChatDao chatDao;

    @Autowired
    public ChatService(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    public List<ChatJson> getChatsFrom(Integer id) {
        chatDao.getChatFrom(id).stream().map()
    }

    public void addChat(Chat chat) {
        chatDao.add(chat);
        chat.start();
    }


}
