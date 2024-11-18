package com.mb.social.network.service;

import com.mb.social.network.model.dto.ConversationResponse;
import com.mb.social.network.model.dto.MessageResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ZaloService {
    void sendMessage(String recipientId, String message);

    List<MessageResponse> getListMessage(String conversationId, int offset, int count) throws UnsupportedEncodingException;
}
