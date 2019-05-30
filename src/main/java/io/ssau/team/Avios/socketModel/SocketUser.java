package io.ssau.team.Avios.socketModel;

import io.ssau.team.Avios.model.User;

import java.net.Socket;

public class SocketUser implements Runnable {
    private Socket socket;

    private User user;

    public SocketUser(Socket socket, User user) {
        this.socket = socket;
        this.user = user;
    }

    @Override
    public void run() {

    }

}
