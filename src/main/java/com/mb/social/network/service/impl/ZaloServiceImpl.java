package com.mb.social.network.service.impl;

import com.mb.social.network.model.dto.MessageResponse;
import com.mb.social.network.model.dto.MessageZalo;
import com.mb.social.network.model.dto.MessageZaloResponse;
import com.mb.social.network.model.dto.SenderResponse;
import com.mb.social.network.service.ZaloService;
import com.mb.social.network.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ZaloServiceImpl implements ZaloService {
    @Value("${zalo.api.url}")
    private String BASE_URL; // https://openapi.zalo.me/v2.0

    @Value("${zalo.api.access.token}")
    private String ACCESS_TOKEN;

    private final RestTemplate restTemplate;

    @Override
    public List<MessageResponse> getListMessage(String conversationId, int offset, int count) {
        try {
            String jsonData = String.format("{\"user_id\":%s,\"offset\":%d,\"count\":%d}", conversationId, offset, count);

            String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/v2.0/oa/conversation")
                    .queryParam("data", jsonData)
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("access_token", ACCESS_TOKEN);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                var body = response.getBody();
                List<MessageZalo> messages = convertJsonToList(body);

                return messages.stream().map(this::toMessageResponse).toList();
            } else {
                throw new RuntimeException("Failed to fetch messages: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error encoding data or calling API: " + e.getMessage(), e);
        }
    }

    public List<MessageZalo> convertJsonToList(String json) throws Exception {
        MessageZaloResponse response = JsonUtil.fromJson(json, MessageZaloResponse.class);

        return response.getData();
    }

    MessageResponse toMessageResponse(MessageZalo item) {
        MessageResponse msg = new MessageResponse();
        msg.setId(item.getMessageId());
        msg.setMessage(item.getMessage());
        msg.setCreatedTime(String.valueOf(item.getTime()));
        msg.setType(item.getType());
        msg.setUrl(item.getUrl());
        msg.setThumb(item.getThumb());

        SenderResponse sender = new SenderResponse();
        sender.setId(item.getFromId());
        sender.setName(item.getFromDisplayName());
        sender.setAvatar(item.getFromAvatar());
        sender.setEmail("");

        msg.setFrom(sender); //set sender

        return msg;
    }

    /**
     * Send message
     *
     * @param recipientId
     * @param message
     */
    @Override
    public void sendMessage(String recipientId, String message) {
        String jsonPayload = String.format(
                "{\"recipient\": {\"user_id\": \"%s\"}, \"message\": {\"text\": \"%s\"}}",
                recipientId, message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("access_token", ACCESS_TOKEN);

        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    BASE_URL + "/v3.0/oa/message/cs", HttpMethod.POST, entity, String.class);

            System.out.println("Response: " + response.getBody());
        } catch (Exception e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
}
