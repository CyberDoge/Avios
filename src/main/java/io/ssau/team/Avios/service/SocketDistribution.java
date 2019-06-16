package io.ssau.team.Avios.service;

import io.ssau.team.Avios.dao.TokenDao;
import io.ssau.team.Avios.dao.UserDao;
import io.ssau.team.Avios.model.User;
import io.ssau.team.Avios.socketModel.SocketUser;
import io.ssau.team.Avios.socketModel.SocketViewer;
import io.ssau.team.Avios.socketService.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
                    Socket socket = serverSocket.accept();
                    socket.setSoTimeout(4000);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String input;
                    try {
                        input = reader.readLine();
                    } catch (SocketTimeoutException e) {
                        socket.close();
                        reader.close();
                        continue;
                    }
                    socket.setSoTimeout(0);
                    if (input.startsWith("theme:")) {
                        String themeId = input.substring(6);
                        chatService.addViewerToChat(new SocketViewer(socket), themeId);
                    } else {
                        User user = userDao.get(tokenDao.get(input));
                        if (user == null) {
                            socket.close();
                            continue;
                        }
                        chatService.addConnectedUserToChat(new SocketUser(socket, user));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
