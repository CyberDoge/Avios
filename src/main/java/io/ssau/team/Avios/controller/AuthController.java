package io.ssau.team.Avios.controller;

import io.ssau.team.Avios.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static io.ssau.team.Avios.param.Header.*;

@RestController
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity auth(@RequestHeader(USERNAME) String username) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(username));
    }

    @GetMapping("/login")
    public ResponseEntity testLogin(@RequestHeader(TOKEN) String token) {
        return ResponseEntity.status(HttpStatus.OK).body("login: " + token);
    }

}
