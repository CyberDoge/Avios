package io.ssau.team.Avios.dao;

import io.ssau.team.Avios.model.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserDao {

    private Set<User> users = new HashSet<>();

    @PostConstruct
    public void init(){
        users.add(new User(1, "User"));
        users.add(new User(2, "Admin"));
        users.add(new User(3, "simple"));
        users.add(new User(4, "first"));
        users.add(new User(5, "second"));
    }

    public boolean contains(String username) {
        return users.stream().anyMatch(user-> user.getUsername().equalsIgnoreCase(username));
    }

    public User get(String username) {
        return users.stream().filter(user-> user.getUsername().equalsIgnoreCase(username)).findAny().orElse(null);
    }

    public void deleteThemesQueue(Integer userId) {
        User userById = getUserById(userId);
        userById.getVoteNoThemes().clear();
        userById.getVoteYesThemes().clear();
    }

    public User create(String username) {
        User user = new User(users.size()+1, username);
        users.add(user);
        return user;
    }

    public boolean subscribeToTheme(Integer id, Integer userId, boolean agree) {
        User user = getUserById(userId);
        if(agree){
            if (user.getVoteYesThemes().remove(id)) return false;
            user.getVoteYesThemes().add(id);
            user.getVoteNoThemes().remove(id);
        }else {
            if (user.getVoteNoThemes().remove(id)) return false;
            user.getVoteYesThemes().remove(id);
            user.getVoteNoThemes().add(id);
        }
        return true;
    }

    public User getUserById(Integer userId){
        return users.stream().filter(user->Objects.equals(user.getId(), userId)).findFirst().orElseThrow();
    }

    public Optional<User> getUserByIdNullable(Integer userId){
        return users.stream().filter(user->Objects.equals(user.getId(), userId)).findFirst();
    }
}
