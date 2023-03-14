package com.db.dbcommunity.feign;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.VisitConstant;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("school-community-visit")
public interface VisitFeignClient {

    @GetMapping(VisitConstant.URI_PREFIX + "/count/article")
    R<SingleKeyVO> getVisitCountByArticleId(@RequestParam("articleId") String articleId);
}
