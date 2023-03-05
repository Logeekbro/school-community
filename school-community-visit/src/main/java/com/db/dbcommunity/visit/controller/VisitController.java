package com.db.dbcommunity.visit.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.visit.service.VisitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class VisitController {

    @Resource
    private VisitService visitService;

    /**
     * 记录用户浏览文章
     */
    @PostMapping("/article")
    public R<Void> handleVisit(@RequestParam Long articleId) {
        return visitService.handleVisit(UserContext.getCurrentUserId(), articleId) ? R.success() : R.failed();
    }

    /**
     * 根据文章id获取文章浏览量
     */
    @GetMapping("/count/article")
    public R<SingleKeyVO> getVisitCountByArticleId(@RequestParam Long articleId) {
        Long count = visitService.getVisitCountByArticleId(articleId);
        return R.success(new SingleKeyVO(count));
    }
}
