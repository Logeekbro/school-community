package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.service.SectionService;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.UserContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Resource
    private SectionService sectionService;

    /**
     * 获取所有分区信息
     */
    @GetMapping("/all")
    public R<List<Map<String, Object>>> getAllSectionInfo() {
        List<Map<String, Object>> list = sectionService.getAllSectionInfo();
        return R.success(list);
    }

    /**
     * 创建分区
     */
    @PostMapping("/")
    public R<SingleKeyVO> addSection(@RequestParam String sectionName) {
        Integer sectionId = sectionService.addSection(UserContext.getCurrentUserId(), sectionName);
        return R.success(new SingleKeyVO(sectionId));
    }

    /**
     * 根据分区id获取分区名称
     */
    @GetMapping("/name/{sectionId}")
    public R<SingleKeyVO> getSectionNameById(@PathVariable String sectionId) {
        String sectionName = sectionService.getSectionNameById(sectionId);
        return R.success(new SingleKeyVO(sectionName));
    }

}
