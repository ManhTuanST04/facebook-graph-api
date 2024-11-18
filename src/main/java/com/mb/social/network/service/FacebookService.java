package com.mb.social.network.service;

import com.mb.social.network.model.dto.ConversationResponse;
import com.mb.social.network.model.dto.MessageResponse;

import java.util.List;

public interface FacebookService {
    void sendMessage(String recipientId, String message);

    List<ConversationResponse> getListConversation();

    List<MessageResponse> getListMessage(String conversationId);


}
