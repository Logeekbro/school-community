package com.db.dbcommunity.score.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.score.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Resource
    private LikeService likeService;

    /**
     * 点赞某种东西
     * @param type 某种东西，比如：文章、评论等
     * @param id 某种东西的id
     */
    @PostMapping("/{type}/id/{id}")
    public R<Void> doLike(@PathVariable String type, @PathVariable Long id) {
        return likeService.changeLike(type, id, UserContext.getCurrentUserId(), true) ? R.success() : R.failed();
    }

    /**
     * 取消点赞某种东西
     * @param type 某种东西，比如：文章、评论等
     * @param id 某种东西的id
     */
    @DeleteMapping("/{type}/id/{id}")
    public R<Void> doUnLike(@PathVariable String type, @PathVariable Long id) {
        return likeService.changeLike(type, id, UserContext.getCurrentUserId(), false) ? R.success() : R.failed();
    }


}
