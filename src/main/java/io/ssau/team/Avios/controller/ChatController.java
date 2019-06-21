package io.ssau.team.Avios.controller;

import io.ssau.team.Avios.model.User;
import io.ssau.team.Avios.socketModel.json.ChatJson;
import io.ssau.team.Avios.socketService.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {
    private ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(path = "/chats/{id}")
    public List<ChatJson> getChats(@PathVariable("id") Integer id) {
        return chatService.getChatsFrom(id);
    }

    @GetMapping(path = "/chat/{id}/{message_index}")
    public ResponseEntity voteForMessage(@PathVariable("id") Integer id, @PathVariable("message_index") Integer messageIndex) {
        Integer userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        chatService.voteForMessage(id, messageIndex, userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}