package io.ssau.team.Avios.dao;

import io.ssau.team.Avios.socketModel.Chat;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;

@Repository
public class ChatDao {
    private static final ArrayList<Chat> chats = new ArrayList<>();

    public void add(Chat chat) {
        chats.add(chat);
    }

    public LinkedList<Chat> getChatFrom(int index) {
        LinkedList<Chat> listChat = new LinkedList<>();
        int from = index * 10;
        if (chats.size() < from) {
            //если номер больше чем количество тем - возвращаем пустой
            return listChat;
        }
        //выщитываем нужное колличество и заполняем
        for (int size = chats.size() > from + 10 ? from + 10 : chats.size(); from < size; from++) {
            listChat.add(chats.get(from));
        }

        return listChat;
    }
}
