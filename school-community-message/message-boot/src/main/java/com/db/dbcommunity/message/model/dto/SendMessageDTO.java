package com.db.dbcommunity.message.model.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class SendMessageDTO implements Serializable {

    private Long senderId;

    private Long targetId;

    private String content;


}
