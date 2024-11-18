package com.mb.social.network.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageZalo {
    @JsonProperty("src")
    private int src;

    @JsonProperty("time")
    private long time;

    @JsonProperty("sent_time")
    private String sentTime;

    @JsonProperty("from_id")
    private String fromId;

    @JsonProperty("from_display_name")
    private String fromDisplayName;

    @JsonProperty("from_avatar")
    private String fromAvatar;

    @JsonProperty("to_id")
    private String toId;

    @JsonProperty("to_display_name")
    private String toDisplayName;

    @JsonProperty("to_avatar")
    private String toAvatar;

    @JsonProperty("message_id")
    private String messageId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("message")
    private String message;

    @JsonProperty("url")
    private String url;

    @JsonProperty("thumb")
    private String thumb;

    @JsonProperty("description")
    private String description;
}
