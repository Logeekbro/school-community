package com.db.dbcommunity.message.controller;


import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.MessageConstant;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.message.document.Message;
import com.db.dbcommunity.message.document.Notion;
import com.db.dbcommunity.message.document.UserPrivateMessage;
import com.db.dbcommunity.message.model.dto.SendMessageDTO;
import com.db.dbcommunity.message.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(MessageConstant.URI_PREFIX)
public class MessageController {

    @Resource
    private MessageService messageService;


    /**
     * 发送消息
     *
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public R<Message> sendMessage(@RequestBody SendMessageDTO messageDTO) {
        messageDTO.setSenderId(UserContext.getCurrentUserId());
        Message messageInfoVO = messageService.createMessage(messageDTO);
        if (messageInfoVO != null) return R.success(messageInfoVO);
        return R.failed();
    }

    /**
     * 获取与用户有关的所有私信已读消息
     */
    @GetMapping("/private/all")
    public R<List<UserPrivateMessage>> getAllMessage() {
        List<UserPrivateMessage> list = messageService.getAllHasReadMessageByUserId(UserContext.getCurrentUserId());
        return R.success(list);
    }

    /**
     * 获取与用户有关的所有私信未读消息
     */
    @GetMapping("/unRead/all")
    public R<List<UserPrivateMessage>> getAllUnReadMessage() {
        List<UserPrivateMessage> list = messageService.getAllUnReadMessageByUserId(UserContext.getCurrentUserId());
        return R.success(list);
    }

    /**
     * 获取所有系统消息
     */
    @GetMapping("/system")
    public R<List<Notion>> getAllSystemMessage() {
        List<Notion> list = messageService.getAllSystemMessageByUserId(UserContext.getCurrentUserId());
        return R.success(list);
    }

    /**
     * 将消息设为已读
     */
    @RequestMapping(value = "/hasRead/{senderId}", method = RequestMethod.PUT)
    public R<Void> updateReadStatus(@PathVariable("senderId") Long senderId) {
        messageService.updateReadStatus(senderId, UserContext.getCurrentUserId());
        return R.success();
    }

    /**
     * 获取用户所有未读消息数
     */
    @RequestMapping(value = "/unReadCount/all", method = RequestMethod.GET)
    public R<SingleKeyVO> getAllUnReadCount() {
        Long count = messageService.getAllUnReadCount(UserContext.getCurrentUserId());
        SingleKeyVO vo = new SingleKeyVO(count);
        return R.success(vo);
    }

}
