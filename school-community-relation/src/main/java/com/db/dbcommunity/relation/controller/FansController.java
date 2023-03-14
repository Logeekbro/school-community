package com.db.dbcommunity.relation.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.MyPage;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.relation.service.FansService;
import com.db.dbcommunity.relation.service.FollowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/fans")
public class FansController {

    @Resource
    private FansService fansService;

    /**
     * 获取用户的粉丝id列表分页数据
     */
    @GetMapping("/list")
    public R<MyPage<String>> getFansList(@RequestParam Long current, @RequestParam Short size) {
        MyPage<String> page = fansService.getFansList(UserContext.getCurrentUserId(), current, size);
        return R.success(page);
    }

    /**
     * 获取用户的粉丝数
     */
    @GetMapping("/count")
    public R<SingleKeyVO> getFansCountByUserId(@RequestParam Long userId) {
        Long count = fansService.getFansCountByUserId(userId);
        return R.success(new SingleKeyVO(count));
    }
}
