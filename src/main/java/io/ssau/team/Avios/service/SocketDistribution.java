package io.ssau.team.Avios.service;

import io.ssau.team.Avios.dao.TokenDao;
import io.ssau.team.Avios.dao.UserDao;
import io.ssau.team.Avios.model.User;
import io.ssau.team.Avios.socketModel.Chat;
import io.ssau.team.Avios.socketModel.SocketUser;
import io.ssau.team.Avios.socketService.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

@Component
public class SocketDistribution {
    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ChatService chatService;

    public void start(ServerSocket serverSocket) {
        CompletableFuture.runAsync(() -> {
            Chat chat = new Chat();
            while (true) {
                try {
                    if (chat == null) {
                        chat = new Chat();
                    }
                    //todo проблема если юзер так и не законектится
                    Socket socket = serverSocket.accept();
                    final char[] userUUID = new char[36];
                    InputStream inputStream = socket.getInputStream();
                    for (int i = 0; i < 36; i++) {
                        userUUID[i] = (char) inputStream.read();
                    }
                    User user = userDao.get(tokenDao.get(new String(userUUID)));
                    if (user == null) {
                        socket.close();
                        continue;
                    }
                    SocketUser socketUser = new SocketUser(socket, user, chat);
                    chat.setUser(socketUser);
                    if (chat.isReady()) {
                        chatService.addChat(chat);
                        chat = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
