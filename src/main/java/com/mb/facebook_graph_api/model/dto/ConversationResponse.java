package com.mb.facebook_graph_api.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationResponse implements Serializable {
    private String id;
    private String updatedTime;
    private String link;
    private Integer messageCount;
    private String snippet;
    private Integer unreadCount;

    private List<SenderResponse> senders;
}
