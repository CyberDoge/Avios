package io.ssau.team.Avios.service;

import io.ssau.team.Avios.dao.TokenDao;
import io.ssau.team.Avios.dao.UserDao;
import io.ssau.team.Avios.model.User;
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
    private TokenDao tokenDao;
    private UserDao userDao;
    private ChatService chatService;

    @Autowired
    public SocketDistribution(TokenDao tokenDao, UserDao userDao, ChatService chatService) {
        this.tokenDao = tokenDao;
        this.userDao = userDao;
        this.chatService = chatService;
    }

    public void start(ServerSocket serverSocket) {
        CompletableFuture.runAsync(() -> {
            while (true) {
                try {
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
                    chatService.addConnectedUserToChat(new SocketUser(socket, user));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
