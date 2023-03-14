package com.db.dbcommunity.feign;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.SearchConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient("school-community-search")
public interface ESFeignClient {

    /**
     * 更新文章索引
     */
    @PutMapping(SearchConstant.URI_PREFIX + "/index/article")
    R<Void> updateArticleIndex(@RequestBody List<Map<String, Object>> articles);

    /**
     * 更新用户索引
     */
    @PutMapping(SearchConstant.URI_PREFIX + "/index/user")
    R<Void> updateUserIndex(@RequestBody List<Map<String, Object>> users);
}
