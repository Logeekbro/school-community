package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.model.dto.ReplyCreateDTO;
import com.db.dbcommunity.article.model.vo.ReplyInListVO;
import com.db.dbcommunity.article.service.ReplyService;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.MyPage;
import com.db.dbcommunity.common.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户删除自己的回复
     */
    @DeleteMapping("/self")
    public R<Void> deleteSelfReply(@RequestParam Long replyId) {
        return replyService.deleteById(UserContext.getCurrentUserId(), replyId) ? R.success() : R.failed();
    }

    /**
     * 删除用户的回复，需要管理员权限
     */
    @DeleteMapping("/user")
    public R<Void> deleteUserReply(@RequestParam Long userId, @RequestParam Long replyId) {
        return replyService.deleteById(userId, replyId) ? R.success() : R.failed();
    }

    /**
     * 获取评论的回复列表
     */
    @GetMapping("/list")
    public R<MyPage<ReplyInListVO>> getReplyList(@RequestParam Long commentId,
                                                 @RequestParam Long current,
                                                 @RequestParam Short size) {
        MyPage<ReplyInListVO> page = replyService.getReplyListByCommentId(commentId, current, size);
        return R.success(page);
    }

    /**
     * 获取评论的回复数量
     */
    @GetMapping("/count")
    public R<SingleKeyVO> getReplyCount(@RequestParam Long commentId) {
        Long count = replyService.getReplyCountByCommentId(commentId);
        return R.success(new SingleKeyVO(count));
    }
}
