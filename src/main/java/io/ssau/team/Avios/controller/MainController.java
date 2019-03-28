package io.ssau.team.Avios.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class MainController {

    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }

    @GetMapping()
    public String index(){
        return "index";
    }

}
