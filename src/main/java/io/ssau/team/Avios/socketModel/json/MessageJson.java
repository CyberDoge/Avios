package io.ssau.team.Avios.socketModel.json;

public class MessageJson {
    public Integer userId;
    public String message;
    public boolean success;
    public short[] votesUp;
    public short[] votesDown;

    public MessageJson(boolean success) {
        this.success = success;
    }

    public MessageJson(String message) {
        this.message = message;
    }

    public MessageJson() {
    }
}
