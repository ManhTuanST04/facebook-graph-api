package com.mb.facebook_graph_api.controller;

import com.mb.facebook_graph_api.model.dto.ConversationResponse;
import com.mb.facebook_graph_api.model.dto.MessageResponse;
import com.mb.facebook_graph_api.model.dto.NewMessageRequest;
import com.mb.facebook_graph_api.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class FacebookController {

    @Autowired
    private FacebookService facebookService;

    @GetMapping("/conversations")
    public List<ConversationResponse> getAllConversation() {
        return facebookService.getListConversation();
    }

    @GetMapping("/messages")
    public List<MessageResponse> getAllMessages(@RequestParam String conversationId) {
        return facebookService.getListMessage(conversationId);
    }

    @PostMapping("/messages")
    public String sendMessage(@RequestBody NewMessageRequest request) {
        facebookService.sendMessage(request.getRecipientId(), request.getMessage());
        return "";
    }
}
