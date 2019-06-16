package io.ssau.team.Avios.socketModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssau.team.Avios.socketModel.json.MessageJson;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class SocketViewer implements Closeable {
    private final PrintWriter writer;
    private Socket socket;
    private ObjectMapper objectMapper;

    public SocketViewer(Socket socket) throws IOException {
        this.socket = socket;
        writer = new PrintWriter(socket.getOutputStream());
        objectMapper = new ObjectMapper();
    }

    public void sendMessage(MessageJson messageJson) throws IOException {
        writer.println(objectMapper.writeValueAsString(messageJson));
        writer.flush();
    }

    public void sendAllMessages(List<MessageJson> messagesJson) throws IOException {
        writer.println(objectMapper.writeValueAsString(messagesJson));
        writer.flush();
    }

    @Override
    public void close() {
        try {
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
