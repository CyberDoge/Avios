package io.ssau.team.Avios.socketModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssau.team.Avios.socketModel.json.MessageJson;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class SocketViewer implements Closeable {
    private final OutputStream outputStream;
    private Socket socket;
    private String ipAddr;
    private ObjectMapper objectMapper;

    public SocketViewer(Socket socket) throws IOException {
        this.socket = socket;
        this.ipAddr = socket.getInetAddress().getHostAddress();
        outputStream = socket.getOutputStream();
        objectMapper = new ObjectMapper();
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void sendMessage(MessageJson messageJson) throws IOException {
        objectMapper.writeValue(outputStream, messageJson);
    }

    public void sendAllMessages(List<MessageJson> messagesJson) throws IOException {
        objectMapper.writeValue(outputStream, messagesJson);
    }

    @Override
    public void close() {
        try {
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
