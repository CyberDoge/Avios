package io.ssau.team.Avios.socketModel;

import io.ssau.team.Avios.model.User;
import io.ssau.team.Avios.socketModel.json.MessageJson;

import java.io.*;
import java.net.Socket;

public class SocketUser implements Runnable, Closeable {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private User user;

    private Chat chat;

    public SocketUser(Socket socket, User user) throws IOException {
        this.socket = socket;
        this.user = user;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());

    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                chat.readMessage(reader.readLine(), this);
            }
        } catch (IOException e) {
            //если чат закончен - нормальная ошибка
            if (!chat.isEnded()) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(MessageJson message) {
        writer.println(message);
    }

    public String getUsername() {
        return user.getUsername();
    }

    public Integer getId() {
        return user.getId();
    }

    @Override
    public void close() {
        try {
            socket.close();
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
