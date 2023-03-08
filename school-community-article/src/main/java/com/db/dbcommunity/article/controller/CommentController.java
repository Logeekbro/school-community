package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.model.vo.CommentCreateVO;
import com.db.dbcommunity.article.model.vo.CommentInListVO;
import com.db.dbcommunity.article.service.CommentService;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.util.MyPage;
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

    /**
     * 用户删除自己的评论
     */
    @DeleteMapping("/self")
    public R<Void> deleteSelfComment(@RequestParam Long commentId) {
        return commentService.deleteById(UserContext.getCurrentUserId(), commentId) ? R.success() : R.failed();
    }

    /**
     * 删除用户的评论，需要管理员权限
     */
    @DeleteMapping("/user")
    public R<Void> deleteUserComment(@RequestParam Long userId, @RequestParam Long commentId) {
        return commentService.deleteById(userId, commentId) ? R.success() : R.failed();
    }


    /**
     * 获取文章的评论列表
     */
    @GetMapping("/list")
    public R<MyPage<CommentInListVO>> getCommentList(@RequestParam Long articleId,
                                                     @RequestParam Long current,
                                                     @RequestParam Short size) {
        MyPage<CommentInListVO> page = commentService.getCommentListByArticleId(articleId, current, size);
        return R.success(page);
    }

}
