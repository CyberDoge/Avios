package io.ssau.team.Avios.socketService;

import io.ssau.team.Avios.dao.ChatDao;
import io.ssau.team.Avios.dao.ThemeDao;
import io.ssau.team.Avios.socketModel.Chat;
import io.ssau.team.Avios.socketModel.SocketUser;
import io.ssau.team.Avios.socketModel.SocketViewer;
import io.ssau.team.Avios.socketModel.db_model.ChatDb;
import io.ssau.team.Avios.socketModel.json.ChatJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ChatService {

    private List<Chat> chatsToRun;
    private ChatDao chatDao;
    private ThemeDao themeDao;

    @Autowired
    public ChatService(ChatDao chatDao, ThemeDao themeDao) {
        this.chatDao = chatDao;
        this.themeDao = themeDao;
        chatsToRun = new ArrayList<>();
    }

    public List<ChatJson> getChatsFrom(Integer id) {
        return chatDao.getChatFrom(id).stream().map(ChatJson::new).collect(Collectors.toList());
    }

    public void addConnectedUserToChat(SocketUser socketUser) {
        //сначала ищем в бд есть ли такая комната и юзер с такой комнотой
        chatDao.getChatWithUser(socketUser.getId()).ifPresentOrElse(c -> {
            findNotStartedChatById(c.id).ifPresentOrElse(chat -> {
                if (chat.setUser(socketUser)) {
                    chat.start();
                }
            }, socketUser::close);
        }, socketUser::close);
    }

    public void addViewerToChat(SocketViewer socketViewer, String themeId) {
        try {
            findChatById(Integer.parseInt(themeId)).ifPresent(chat -> {
                chat.addSocketViewer(socketViewer);
            });
        } catch (NumberFormatException e) {
            socketViewer.close();
        }
    }

    private Optional<Chat> findNotStartedChatById(Integer id) {
        return chatsToRun.stream().filter(chat -> Objects.equals(id, chat.getChatId()) && !chat.isReady()).findFirst();
    }

    private Optional<Chat> findChatById(Integer id) {
        return chatsToRun.stream().filter(chat -> Objects.equals(id, chat.getChatId())).findFirst();
    }

    public void createChat(Integer firstUserId, Integer secondUserId, Integer themeId) {
        ChatDb chatDb = new ChatDb();
        chatDb.id = (int) (Math.random() * Integer.MAX_VALUE);
        chatDb.firstUserId = firstUserId;
        chatDb.secondUserId = secondUserId;
        chatDb.themeId = themeId;
        chatDao.add(chatDb);
        chatsToRun.add(new Chat(this, chatDb.id, themeId));
    }

    public void deleteChat(Integer chatId) {
        findChatById(chatId).ifPresent(theme -> themeDao.deleteById(theme.getThemeId()));
        chatsToRun.removeIf(chat -> Objects.equals(chat.getChatId(), chatId));
        chatDao.deleteChatById(chatId);
    }


}
