package io.ssau.team.Avios.model.json;

import io.ssau.team.Avios.model.User;

public class UserJson {
    public Integer id;
    public String username;
    public UserJson(User user){
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
