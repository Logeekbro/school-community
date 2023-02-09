package com.db.dbcommunity.message.service.impl;


import com.db.dbcommunity.common.exception.ApiAsserts;
import com.db.dbcommunity.message.document.LikeNotion;
import com.db.dbcommunity.message.document.Message;
import com.db.dbcommunity.message.document.Notion;
import com.db.dbcommunity.message.document.UserPrivateMessage;
import com.db.dbcommunity.message.model.dto.SendMessageDTO;
import com.db.dbcommunity.message.service.MessageService;
import com.db.dbcommunity.message.util.MongoUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.db.dbcommunity.common.constant.MessageConstant.SYSTEM_ID;


@Service
public class MessageServiceImpl implements MessageService {


    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public Message createMessage(SendMessageDTO messageDTO) {
        if(Objects.equals(messageDTO.getTargetId(), messageDTO.getSenderId())) ApiAsserts.fail("不能发消息给自己");
        UserPrivateMessage userPrivateMessage = new UserPrivateMessage(messageDTO.getSenderId(), messageDTO.getTargetId(), messageDTO.getContent());
        return mongoTemplate.insert(userPrivateMessage);
    }

    @Override
    public List<UserPrivateMessage> getAllHasReadMessageByUserId(Long userId) {
        Query query = new Query(MongoUtil.notDeletedCriteria().
                and("isRead").is(true).
                orOperator(Criteria.where("senderId").is(userId), Criteria.where("targetId").is(userId)));
        return mongoTemplate.find(query, UserPrivateMessage.class);
    }

    @Override
    public void updateReadStatus(Long senderId, Long userId) {
        Query query;
        Class<? extends Message> target;
        if(Objects.equals(senderId, SYSTEM_ID)) {
            query = new Query(MongoUtil.notDeletedCriteria().
                    and("isRead").is(false).
                    and("targetId").is(userId));
            target = LikeNotion.class;
        }
        else {
            query = new Query(MongoUtil.notDeletedCriteria().
                    and("isRead").is(false).
                    and("senderId").is(senderId).
                    and("targetId").is(userId));
            target = UserPrivateMessage.class;
        }
        Update update = new Update();
        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, target);
    }


    @Override
    public Long getAllUnReadCount(Long userId) {
        Query query = new Query(MongoUtil.notDeletedCriteria().
                and("isRead").is(false).
                and("targetId").is(userId));
        return mongoTemplate.count(query, UserPrivateMessage.class) + mongoTemplate.count(query, Notion.class);
    }


    @Override
    public List<UserPrivateMessage> getAllUnReadMessageByUserId(Long userId) {
        Query query = new Query(MongoUtil.notDeletedCriteria().
                and("isRead").is(false).
                and("targetId").is(userId));
        return mongoTemplate.find(query, UserPrivateMessage.class);
    }

    @Override
    public List<Notion> getAllSystemMessageByUserId(Long userId) {
        Query query = new Query(MongoUtil.notDeletedCriteria().and("targetId").is(userId));
        List<Notion> all = mongoTemplate.find(query, Notion.class);
        return all;
    }

}




