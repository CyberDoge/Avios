package io.ssau.team.Avios.socketModel.json;

import java.util.ArrayList;
import java.util.List;

public class MessageJson {
    private Integer id;
    private Integer userId;
    private String message;
    private boolean success;
    private List<Integer> votedUser;

    public MessageJson(Integer id, Integer userId, String message, boolean success) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.success = success;
        votedUser = new ArrayList<>();
    }


    public MessageJson(MessageJson messageJson) {
        this.id = messageJson.id;
        this.userId = messageJson.userId == Integer.MAX_VALUE ? Integer.MAX_VALUE : -1;
        this.message = messageJson.message;
        this.success = messageJson.success;
        votedUser = new ArrayList<>();
    }


    public MessageJson() {
    }

    public boolean vote(Integer userId) {
        if (votedUser.contains(userId)) {
            votedUser.remove(userId);
            return false;
        }
        votedUser.add(userId);
        return true;
    }

    public Integer getUserId() {
        return userId;
    }

    public int getVotes() {
        return votedUser.size();
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
