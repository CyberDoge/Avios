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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ChatService {

    private Map<Integer, Chat> chatsToRun;
    private ChatDao chatDao;
    private ThemeDao themeDao;

    @Autowired
    public ChatService(ChatDao chatDao, ThemeDao themeDao) {
        this.chatDao = chatDao;
        this.themeDao = themeDao;
        chatsToRun = new HashMap<>();
    }

    public void voteForMessage(Integer chatId, Integer messageIndex, Integer userId) {
        findChatById(chatId).ifPresent(chat -> chat.voteForMessage(messageIndex, userId));
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
            findChatById(Integer.parseInt(themeId)).ifPresentOrElse(chat -> {
                chat.addSocketViewer(socketViewer);
            }, socketViewer::close);
        } catch (NumberFormatException e) {
            socketViewer.close();
        }
    }

    private Optional<Chat> findNotStartedChatById(Integer id) {
        Chat value = chatsToRun.get(id);
        if (value != null && !value.isEnded()) {
            return Optional.of(value);
        }
        return Optional.empty();
    }

    private Optional<Chat> findChatById(Integer id) {
        return Optional.ofNullable(chatsToRun.get(id));
    }

    public void createChat(Integer firstUserId, Integer secondUserId, Integer themeId) {
        ChatDb chatDb = new ChatDb();
        chatDb.id = 1;
        chatDb.firstUserId = firstUserId;
        chatDb.secondUserId = secondUserId;
        chatDb.themeId = themeId;
        chatDao.add(chatDb);
        chatsToRun.put(chatDb.id, new Chat(this, chatDb.id, themeId));
    }

    public void deleteChat(Integer chatId) {
        findChatById(chatId).ifPresent(theme -> themeDao.deleteById(theme.getThemeId()));
        chatsToRun.remove(chatId);
        chatDao.deleteChatById(chatId);
    }


}
