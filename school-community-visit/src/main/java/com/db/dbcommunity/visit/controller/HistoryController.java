package com.db.dbcommunity.visit.controller;


import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.visit.model.vo.HistoryWithDateVO;
import com.db.dbcommunity.visit.service.HistoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 根据日期获取用户的文章浏览记录
     */
    @GetMapping("/date")
    public R<HistoryWithDateVO> getUserHistoryByDate(@RequestParam String targetDate) {
        HistoryWithDateVO result = historyService.getUserHistoryByDate(UserContext.getCurrentUserId(), targetDate);
        return R.success(result);
    }
}
