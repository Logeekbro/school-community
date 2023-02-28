package com.db.dbcommunity.relation.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.UserConstant;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.relation.service.FollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Resource
    private FollowService followService;

    /**
     * 关注某个用户
     * @param beFollowUserId 要关注的用户id
     */
    @PostMapping("/{beFollowUserId}")
    public R<Void> addFollowRelation(@PathVariable Long beFollowUserId) {
        return followService.addFollow(UserContext.getCurrentUserId(), beFollowUserId) ? R.success() : R.failed();
    }

    /**
     * 取消关注某个用户
     */
    @DeleteMapping("/{beFollowUserId}")
    public R<Void> deleteFollowRelation(@PathVariable Long beFollowUserId) {
        return followService.deleteFollow(UserContext.getCurrentUserId(), beFollowUserId) ? R.success() : R.failed();
    }
}
