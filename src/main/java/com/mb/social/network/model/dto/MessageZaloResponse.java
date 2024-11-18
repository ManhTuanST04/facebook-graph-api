package com.mb.social.network.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageZaloResponse implements Serializable {
    @JsonProperty("data")
    private List<MessageZalo> data;

    @JsonProperty("error")
    private int error;

    @JsonProperty("message")
    private String message;
}
