package io.ssau.team.Avios.socketModel;

public class Chat {
    private SocketUser firstUser;
    private SocketUser secondUser;

    private boolean ready = false;

    public void sendMessage(Integer userId, String message) {

    }

    public boolean isReady() {
        return ready;
    }

    public void setUser(SocketUser socketUser) {
        if (firstUser == null) {
            this.firstUser = socketUser;
        } else {
            this.secondUser = socketUser;
            this.ready = true;
        }
    }
}
