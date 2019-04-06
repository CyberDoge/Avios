package io.ssau.team.Avios.model;

public class Room {
    final private Integer themeId;
    final private UserPair userPair;
    private Integer id;

    public Room(Integer themeId, Integer votedYesUserId, Integer votedNoUserId) {

        this.themeId = themeId;
        this.userPair = new UserPair(votedYesUserId, votedNoUserId);
    }

    public Integer getThemeId() {
        return themeId;
    }

    public Integer getVotedYesUserId() {
        return userPair.votedYesUserId;
    }

    public Integer getVotedNoUserId() {
        return userPair.votedNoUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private final class UserPair {
        final Integer votedYesUserId;
        final Integer votedNoUserId;

        UserPair(Integer votedYesUserId, Integer votedNoUserId) {
            this.votedYesUserId = votedYesUserId;
            this.votedNoUserId = votedNoUserId;
        }
    }
}
