package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.model.vo.CommentCreateVO;
import com.db.dbcommunity.article.service.CommentService;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.util.UserContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 添加评论
     *
     */
    @PostMapping(value = "/")
    public R<Void> addComment(@RequestBody CommentCreateVO vo) {
        return commentService.createComment(UserContext.getCurrentUserId(), vo) ? R.success() : R.failed();
    }
}
