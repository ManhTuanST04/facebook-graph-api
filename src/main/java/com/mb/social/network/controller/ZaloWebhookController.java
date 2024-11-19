package com.mb.social.network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("v1/zalo/webhook")
public class ZaloWebhookController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        // Xử lý sự kiện từ payload
        String eventName = (String) payload.get("event_name");

        if ("user_send_text".equals(eventName) || "user_send_image".equals(eventName) || "user_send_sticker".equals(eventName)) {
            messagingTemplate.convertAndSend("/topic/messages", payload);

            System.out.println("User sent a text message: " + payload);
        }

        // Trả về phản hồi cho server
        return ResponseEntity.ok("Webhook received successfully");
    }

}
