package com.db.dbcommunity.message.service;


import com.db.dbcommunity.message.document.Message;
import com.db.dbcommunity.message.document.Notion;
import com.db.dbcommunity.message.document.UserPrivateMessage;
import com.db.dbcommunity.message.model.dto.SendMessageDTO;

import java.util.List;

/**
* @author bin
* @description 针对表【tb_message】的数据库操作Service
* @createDate 2022-07-04 18:13:16
*/
public interface MessageService {

    Message createMessage(SendMessageDTO messageDTO);

    List<UserPrivateMessage> getAllHasReadMessageByUserId(Long userId);

    void updateReadStatus(Long senderId, Long userId);

    Long getAllUnReadCount(Long userId);

    List<UserPrivateMessage> getAllUnReadMessageByUserId(Long userId);

    List<Notion> getAllSystemMessageByUserId(Long userId);
}
