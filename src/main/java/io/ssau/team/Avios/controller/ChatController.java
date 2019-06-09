package io.ssau.team.Avios.controller;

import io.ssau.team.Avios.socketModel.json.ChatJson;
import io.ssau.team.Avios.socketService.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ChatController {
    private ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(path = "/chat/{id}")
    public List<ChatJson> getChats(@PathVariable("id") Integer id) {
        chatService.
    }
}
