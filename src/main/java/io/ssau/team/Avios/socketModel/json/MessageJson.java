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

    public MessageJson(Integer userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public MessageJson() {
    }
}
