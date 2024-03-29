package com.db.dbcommunity.visit.controller;


import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
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
    @PostMapping(value = "/article", name = "记录用户的文章浏览记录_true")
    public R<Void> saveArticleHistory(@RequestParam Long articleId) {
        return historyService.add(UserContext.getCurrentUserId(), articleId) ? R.success() : R.failed();
    }

    /**
     * 根据日期获取用户的文章浏览记录
     */
    @GetMapping(value = "/date", name = "根据日期获取用户的文章浏览记录_true")
    public R<HistoryWithDateVO> getUserHistoryByDate(@RequestParam String targetDate) {
        HistoryWithDateVO result = historyService.getUserHistoryByDate(UserContext.getCurrentUserId(), targetDate);
        return R.success(result);
    }

    /**
     * 根据id删除浏览记录
     */
    @DeleteMapping(value = "/id", name = "根据id删除浏览记录_true")
    public R<Void> deleteHistoryById(@RequestParam Long historyId) {
        return historyService.deleteHistoryById(UserContext.getCurrentUserId(), historyId) ? R.success() : R.failed();
    }

    /**
     * 删除用户的所有历史记录
     */
    @DeleteMapping(value = "/all", name = "删除用户的所有历史记录_true")
    public R<SingleKeyVO> deleteAllHistoryByUserId() {
        int count = historyService.deleteAllHistoryByUserId(UserContext.getCurrentUserId());
        return R.success(new SingleKeyVO(count));
    }
}
