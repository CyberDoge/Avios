package io.ssau.team.Avios.socketController;

import io.ssau.team.Avios.service.SocketDistribution;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {
    private ServerSocket serverSocket;

    public void init() throws IOException {
        serverSocket = new ServerSocket(3030);
        new SocketDistribution(serverSocket);
    }
}
