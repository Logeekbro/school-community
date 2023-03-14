package com.db.dbcommunity.feign;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.ScoreConstant;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

@FeignClient("school-community-score")
public interface ScoreFeignClient {

    @GetMapping(ScoreConstant.URI_PREFIX + "/like/count/article/id/{id}")
    R<SingleKeyVO> getArticleLikeCount(@PathVariable("id") Serializable id);
}
