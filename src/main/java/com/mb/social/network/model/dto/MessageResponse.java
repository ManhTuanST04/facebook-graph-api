package com.mb.social.network.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse implements Serializable {
    private String id;
    private String message;
    private String createdTime;
    private SenderResponse from;

    private String type;
    private String url;
    private String thumb;
}
