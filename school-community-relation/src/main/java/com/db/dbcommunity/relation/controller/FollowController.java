package com.db.dbcommunity.relation.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.UserConstant;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.MyPage;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.relation.service.FollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

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
        return followService
                .changeFollow(UserContext.getCurrentUserId(), beFollowUserId, true) ? R.success() : R.failed();
    }

    /**
     * 取消关注某个用户
     */
    @DeleteMapping("/{beFollowUserId}")
    public R<Void> deleteFollowRelation(@PathVariable Long beFollowUserId) {
        return followService
                .changeFollow(UserContext.getCurrentUserId(), beFollowUserId, false) ? R.success() : R.failed();
    }

    /**
     * 查询是否关注某个用户
     */
    @GetMapping("/check/{beFollowUserId}")
    public R<SingleKeyVO> checkIsFollow(@PathVariable Long beFollowUserId) {
        boolean result = followService.isFollow(UserContext.getCurrentUserId(), beFollowUserId);
        SingleKeyVO singleKeyVO = new SingleKeyVO(result);
        return R.success(singleKeyVO);
    }

    /**
     * 获取关注列表
     */
    @GetMapping("/list")
    public R<MyPage<Long>> getFollowList(@RequestParam Long current, @RequestParam Short size) {
        MyPage<Long> list = followService.getFollowList(UserContext.getCurrentUserId(), current, size);
        return R.success(list);
    }

    /**
     * 根据用户id获取用户的关注列表
     */
    @GetMapping("/list/userId/{userId}")
    public R<MyPage<Long>> getFollowListByUserId(@RequestParam Long current, @RequestParam Short size,
                                                 @PathVariable Long userId) {
        MyPage<Long> list = followService.getFollowList(userId, current, size);
        return R.success(list);
    }
}
