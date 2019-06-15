package io.ssau.team.Avios.socketService;

import io.ssau.team.Avios.dao.ChatDao;
import io.ssau.team.Avios.socketModel.Chat;
import io.ssau.team.Avios.socketModel.db_model.ChatDb;
import io.ssau.team.Avios.socketModel.json.ChatJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatService {


    private ChatDao chatDao;

    @Autowired
    public ChatService(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    public List<ChatJson> getChatsFrom(Integer id) {
        return chatDao.getChatFrom(id).stream().map(ChatJson::new).collect(Collectors.toList());
    }

    public void addChat(Chat chat) {
        chatDao.add(new ChatDb(chat));
        chat.start();
    }


}
