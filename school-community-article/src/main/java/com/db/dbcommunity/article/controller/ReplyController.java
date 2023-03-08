package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.model.dto.ReplyCreateDTO;
import com.db.dbcommunity.article.service.ReplyService;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Resource
    private ReplyService replyService;

    /**
     * 添加回复
     */
    @PostMapping("/")
    public R<Void> addReply(@RequestBody @Validated ReplyCreateDTO dto) {
        return replyService.saveReply(UserContext.getCurrentUserId(), dto) ? R.success() : R.failed();
    }


}
