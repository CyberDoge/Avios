package io.ssau.team.Avios.dao;

import io.ssau.team.Avios.model.Room;

import java.util.*;

public class RoomDao {
    private final List<Room> rooms = new LinkedList<>();

    public void add(Room room) {
        rooms.add(room);
    }

    public void removeById(Integer id) {
        Iterator<Room> iterator = rooms.iterator();
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next().getId(), id)) {
                iterator.remove();
                break;
            }
        }
    }

    public Optional<Room> getByUserId(Integer userId) {
        return rooms.parallelStream().filter(room ->
                Objects.equals(room.getVotedYesUserId(), userId) || Objects.equals(room.getVotedNoUserId(), userId)
        ).findAny();
    }
}
