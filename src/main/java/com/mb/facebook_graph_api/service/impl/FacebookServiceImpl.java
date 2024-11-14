package com.mb.facebook_graph_api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mb.facebook_graph_api.model.dto.ConversationResponse;
import com.mb.facebook_graph_api.model.dto.MessageResponse;
import com.mb.facebook_graph_api.model.dto.SenderResponse;
import com.mb.facebook_graph_api.service.FacebookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacebookServiceImpl implements FacebookService {

    @Value("${facebook.api.url}")
    private String FACEBOOK_API_URL;

    @Value("${facebook.page.access.token}")
    private String ACCESS_TOKEN;

    @Value("${facebook.page.id}")
    private String PAGE_ID;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<ConversationResponse> getListConversation() {
        // 416544284885518/conversations?fields=name,id,updated_time,link,message_count,snippet,unread_count
        String url = UriComponentsBuilder.fromHttpUrl(FACEBOOK_API_URL)
                .pathSegment(PAGE_ID, "conversations")
                .queryParam("fields", "name,id,updated_time,link,message_count,snippet,unread_count,senders")
                .queryParam("access_token", ACCESS_TOKEN)
                .toUriString();

        // Gọi API sử dụng RestTemplate
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<ConversationResponse> conversations = new ArrayList<>();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");

            for (Map<String, Object> item : data) {
                ConversationResponse conversation = toConversationResponse(item);
                conversations.add(conversation);
            }
        }

        return conversations;
    }

    ConversationResponse toConversationResponse(Map<String, Object> item) {
        ConversationResponse conversation = new ConversationResponse();
        conversation.setId(item.get("id").toString());
        conversation.setUpdatedTime(item.get("updated_time").toString());
        conversation.setMessageCount(Integer.parseInt(item.get("message_count").toString()));
        conversation.setSnippet(item.get("snippet").toString());
        conversation.setLink(item.get("link").toString());
        conversation.setUnreadCount(Integer.parseInt(item.get("unread_count").toString()));

        Map<String, Object> sendersData = (Map<String, Object>) item.get("senders");
        if (sendersData != null) {
            conversation.setSenders(toListSenderResponse(sendersData));
        }

        return conversation;
    }

    List<SenderResponse> toListSenderResponse(Map<String, Object> fromData) {
        List<Map<String, Object>> msgList = (List<Map<String, Object>>) fromData.get("data");
        List<SenderResponse> senders = new ArrayList<>();
        if (msgList != null) {
            for (Map<String, Object> senderItem : msgList) {
                SenderResponse sender = objectMapper.convertValue(senderItem, SenderResponse.class);
                senders.add(sender);
            }
        }

        return senders;
    }

    @Override
    public List<MessageResponse> getListMessage(String conversationId) {
        String url = UriComponentsBuilder.fromHttpUrl(FACEBOOK_API_URL)
                .pathSegment(conversationId, "messages")
                .queryParam("fields", "message,created_time,from,id")
                .queryParam("access_token", ACCESS_TOKEN)
                .toUriString();

        // Gọi API với RestTemplate
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        List<MessageResponse> messages = new ArrayList<>();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");

            for (Map<String, Object> item : data) {
                MessageResponse conversation = toMessageResponse(item);
                messages.add(conversation);
            }
        }

        return messages;
    }

    MessageResponse toMessageResponse(Map<String, Object> item) {
        MessageResponse msg = new MessageResponse();
        msg.setId(item.get("id").toString());
        msg.setMessage(item.get("message").toString());
        msg.setCreatedTime(item.get("created_time").toString());

        Map<String, Object> fromData = (Map<String, Object>) item.get("from");
        if (fromData != null) {
            SenderResponse sender = objectMapper.convertValue(fromData, SenderResponse.class);
            msg.setFrom(sender);
        }

        return msg;
    }

    @Override
    public void sendMessage(String recipientId, String message) {
        String url = UriComponentsBuilder.fromHttpUrl(FACEBOOK_API_URL)
                .pathSegment("me", "messages")
                .queryParam("access_token", ACCESS_TOKEN)
                .toUriString();

        Map<String, Object> messagePayload = Map.of(
                "recipient", Map.of("id", recipientId),
                "message", Map.of("text", message)
        );

        restTemplate.postForEntity(url, messagePayload, Map.class);
    }
}
