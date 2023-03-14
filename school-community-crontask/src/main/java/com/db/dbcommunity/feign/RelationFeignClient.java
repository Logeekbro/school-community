package com.db.dbcommunity.feign;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.RelationConstant;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("school-community-relation")
public interface RelationFeignClient {

    @GetMapping(RelationConstant.URI_PREFIX + "/fans/count")
    R<SingleKeyVO> getFansCountByUserId(@RequestParam("userId") String userId);
}
