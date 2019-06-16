package io.ssau.team.Avios.socketModel;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

public class SocketViewer implements Closeable {
    private Socket socket;
    private String ipAddr;

    public SocketViewer(Socket socket) {
        this.socket = socket;
        this.ipAddr = socket.getInetAddress().getHostAddress();
    }

    public String getIpAddr() {
        return ipAddr;
    }

    @Override
    public void close() {
        try {
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
