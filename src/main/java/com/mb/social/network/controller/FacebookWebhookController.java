package com.mb.social.network.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/webhook")
public class FacebookWebhookController {
    private static final String VERIFY_TOKEN = "EAAMgSNNBMxsBO5tqKpq8trDDQcGK1ihCeL52D11ssB7PiOwXRqvgpqhMBd2ZAQKIRMwkJP4RVLBiu8T0qHHGjuPYRIwwIDofiFWBrHfu7KmZBi5g2vLvcpFJZBtWgqjpZCcGn67Xo6S4Bd9KZA08w87487bIHIqB6pZArvvUzd7J36y4afw6xZBZBdFC2RTcrOsFn0qCCvLhBJDGQMyzDwZDZD";

    // Endpoint để Facebook xác thực webhook
    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String verifyToken,
            @RequestParam("hub.challenge") String challenge) {

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(verifyToken)) {
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Verification failed");
        }
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        // Xử lý dữ liệu từ payload, có thể bao gồm thông tin về tin nhắn mới
        System.out.println("Webhook event received: " + payload);

        // Trích xuất dữ liệu từ payload nếu cần
        // payload chứa thông tin về sender, message, và page ID.

        return ResponseEntity.ok("EVENT_RECEIVED");
    }

}
