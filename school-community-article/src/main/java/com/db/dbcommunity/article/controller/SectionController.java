package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.service.SectionService;
import com.db.dbcommunity.common.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
