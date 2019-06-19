package io.ssau.team.Avios.dao;

import io.ssau.team.Avios.socketModel.db_model.ChatDb;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ChatDao {
    private static final ArrayList<ChatDb> chats = new ArrayList<>();

    public void add(ChatDb chat) {
        chats.add(chat);
    }

    public Optional<ChatDb> getChatWithUser(Integer userId) {
        return chats.stream().filter(chat -> (Objects.equals(userId, chat.firstUserId) || Objects.equals(userId, chat.secondUserId))).findFirst();
    }

    public ArrayList<ChatDb> getChatFrom(int index) {
        ArrayList<ChatDb> listChat = new ArrayList<>(10);
        int from = index * 10;

        //выщитываем нужное колличество и заполняем
        for (int i = chats.size() - 1 - from, n = i - 10; i > n && i >= 0; i--) {
            listChat.add(chats.get(i));
        }
        return listChat;
    }

    public void deleteChatById(Integer chatId) {
        chats.removeIf(chatDb -> Objects.equals(chatDb.id, chatId));
    }
}
