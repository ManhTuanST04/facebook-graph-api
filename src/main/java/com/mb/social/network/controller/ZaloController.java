package com.mb.social.network.controller;

import com.mb.social.network.model.dto.MessageResponse;
import com.mb.social.network.model.dto.NewMessageRequest;
import com.mb.social.network.service.ZaloService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/v1/zalo")
@AllArgsConstructor
public class ZaloController {
    private final ZaloService zaloService;

    @GetMapping("/messages")
    public List<MessageResponse> getAllMessages(@RequestParam String conversationId, @RequestParam int offset, @RequestParam int count) throws UnsupportedEncodingException {
        return zaloService.getListMessage(conversationId, offset, count);
    }

    @PostMapping("/messages")
    public String sendMessage(@RequestBody NewMessageRequest request) {
        zaloService.sendMessage(request.getRecipientId(), request.getMessage());
        return "";
    }
}
