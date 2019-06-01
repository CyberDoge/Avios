package io.ssau.team.Avios.service;

import io.ssau.team.Avios.dao.TokenDao;
import io.ssau.team.Avios.dao.UserDao;
import io.ssau.team.Avios.socketController.ChatController;
import io.ssau.team.Avios.socketModel.Chat;
import io.ssau.team.Avios.socketModel.SocketUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class SocketDistribution {
    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ChatController chatController;

    public SocketDistribution(ServerSocket serverSocket) {
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
                    SocketUser socketUser = new SocketUser(socket, userDao.get(tokenDao.get(new String(userUUID))), chat);
                    chat.setUser(socketUser);
                    if (chat.isReady()) {
                        chatController.addChat(chat);
                        chat = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
