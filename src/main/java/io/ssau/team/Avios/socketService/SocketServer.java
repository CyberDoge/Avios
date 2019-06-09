package io.ssau.team.Avios.socketService;

import io.ssau.team.Avios.service.SocketDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;

@Component
public class SocketServer {
    private ServerSocket serverSocket;
    @Autowired
    private SocketDistribution socketDistribution;

    @PostConstruct
    public void init() throws IOException {
        serverSocket = new ServerSocket(3030);
        socketDistribution.start(serverSocket);
    }
}
