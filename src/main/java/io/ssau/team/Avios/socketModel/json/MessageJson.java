package io.ssau.team.Avios.socketModel.json;

import java.util.List;

public class MessageJson {
    private Integer userId;
    private String message;
    private boolean success;
    private List<Integer> votedUser;

    public MessageJson(boolean success) {
        this.success = success;
    }

    public MessageJson(Integer userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public void vote(Integer userId) {
        if (votedUser.contains(userId)) {
            votedUser.remove(userId);
        } else {
            votedUser.add(userId);
        }
    }

    public MessageJson() {
    }

    public Integer getUserId() {
        return userId;
    }

    public int getVotes() {
        return votedUser.size();
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
