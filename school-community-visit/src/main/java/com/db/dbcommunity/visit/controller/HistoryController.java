package com.db.dbcommunity.visit.controller;


import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.visit.service.HistoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Resource
    private HistoryService historyService;

    /**
     * 记录用户的文章浏览记录
     */
    @PostMapping("/article")
    public R<Void> saveArticleHistory(@RequestParam Long articleId) {
        return historyService.add(UserContext.getCurrentUserId(), articleId) ? R.success() : R.failed();
    }
}
