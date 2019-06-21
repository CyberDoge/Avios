package io.ssau.team.Avios.dao;

import io.ssau.team.Avios.model.Room;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RoomDao {
    private final List<Room> rooms = new LinkedList<>();

    public void add(Room room) {
        rooms.add(room);
    }

    public void addAll(List<Room> roomList) {
        this.rooms.addAll(roomList);
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

    public void removeByThemeId(Integer themeId){
        Iterator<Room> iterator = rooms.iterator();
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next().getThemeId(), themeId)) {
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
