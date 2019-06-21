package io.ssau.team.Avios.controller;

import io.ssau.team.Avios.model.json.UserJson;
import io.ssau.team.Avios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public UserJson getUserById(@PathVariable("id") Integer id){
        return userService.getUserById(id);
    }
}
