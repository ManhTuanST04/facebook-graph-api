package com.mb.facebook_graph_api.service;

import com.mb.facebook_graph_api.model.dto.ConversationResponse;
import com.mb.facebook_graph_api.model.dto.MessageResponse;

import java.util.List;

public interface FacebookService {
    void sendMessage(String recipientId, String message);

    List<ConversationResponse> getListConversation();

    List<MessageResponse> getListMessage(String conversationId);


}
