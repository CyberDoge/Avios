package io.ssau.team.Avios.socketModel;

import io.ssau.team.Avios.model.User;
import org.apache.commons.io.IOUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

public class SocketUser implements Runnable, Closeable {
    private InputStream inputStream;
    private OutputStream outputStream;

    private Socket socket;
    private User user;

    private Chat chat;

    public SocketUser(Socket socket, User user, Chat chat) throws IOException {
        this.socket = socket;
        this.user = user;
        this.chat = chat;
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public void run() {
        try {
            //todo если вышел игра заканчивается
            String message = IOUtils.toString(inputStream, Charset.defaultCharset());
            chat.readMessage(message, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {
        IOUtils.write(message, outputStream, Charset.defaultCharset());
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
