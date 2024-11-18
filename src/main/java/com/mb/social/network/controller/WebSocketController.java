package com.mb.social.network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessageToClient(String topic, Object payload) {
        messagingTemplate.convertAndSend(topic, payload);
    }
}
