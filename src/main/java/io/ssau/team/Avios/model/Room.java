package io.ssau.team.Avios.model;

public class Room {
    final private Integer id;
    final private Integer themeId;
    final private UserPair userPair;

    public Room(Integer id, Integer themeId, Integer votedYesUserId, Integer votedNoUserId) {
        this.id = id;
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

    private final class UserPair {
        final Integer votedYesUserId;
        final Integer votedNoUserId;

        UserPair(Integer votedYesUserId, Integer votedNoUserId) {
            this.votedYesUserId = votedYesUserId;
            this.votedNoUserId = votedNoUserId;
        }
    }
}
