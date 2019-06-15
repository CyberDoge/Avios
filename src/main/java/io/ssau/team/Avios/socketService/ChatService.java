package io.ssau.team.Avios.socketService;

import io.ssau.team.Avios.dao.ChatDao;
import io.ssau.team.Avios.socketModel.Chat;
import io.ssau.team.Avios.socketModel.SocketUser;
import io.ssau.team.Avios.socketModel.db_model.ChatDb;
import io.ssau.team.Avios.socketModel.json.ChatJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ChatService {

    private List<Chat> chatsToRun;
    private ChatDao chatDao;

    @Autowired
    public ChatService(ChatDao chatDao) {
        this.chatDao = chatDao;
        chatsToRun = new ArrayList<>();
    }

    public List<ChatJson> getChatsFrom(Integer id) {
        return chatDao.getChatFrom(id).stream().map(ChatJson::new).collect(Collectors.toList());
    }

    //todo закрывать и удалять все чаты после игры или по окончанию таймаута ожидания!
    public void addConnectedUserToChat(SocketUser socketUser) {
        //сначала ищем в бд есть ли такая комната и юзер с такой комнотой
        chatDao.getChatWithUser(socketUser.getId()).ifPresentOrElse(c -> {
            Chat chat = findChatById(c.id);
            if (chat.setUser(socketUser)) {
                chat.start();
            }
        }, socketUser::close);
    }

    private Chat findChatById(Integer id) {
        return chatsToRun.stream().filter(chat -> Objects.equals(id, chat.getChatId()) && !chat.isReady()).findFirst().get();
    }

    public void createChat(Integer firstUserId, Integer secondUserId, Integer themeId) {
        ChatDb chatDb = new ChatDb();
        chatDb.id = (int) (Math.random() * Integer.MAX_VALUE);
        chatDb.firstUserId = firstUserId;
        chatDb.secondUserId = secondUserId;
        chatDb.themeId = themeId;
        chatDao.add(chatDb);
        chatsToRun.add(new Chat(chatDb.id, themeId));
    }


}
