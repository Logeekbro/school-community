package com.db.dbcommunity.user.feign;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.SearchConstant;
import com.db.dbcommunity.user.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("school-community-search")
public interface ESFeignClient {

    /**
     * 将用户索引至ES
     */
    @PostMapping(SearchConstant.URI_PREFIX + "/index/user")
    R<Void> indexUser(@RequestBody List<User> users);

    /**
     * 将用户索引至ES
     */
    @PostMapping(SearchConstant.URI_PREFIX + "/index/user")
    R<Void> indexUser(@RequestBody User[] users);

    /**
     * 更新用户索引
     */
    @PutMapping(SearchConstant.URI_PREFIX + "/index/user")
    R<Void> updateUserIndex(@RequestBody List<User> users);

    /**
     * 更新用户索引
     */
    @PutMapping(SearchConstant.URI_PREFIX + "/index/user")
    R<Void> updateUserIndex(@RequestBody User[] users);

    /**
     * 根据id列表将用户数据从ES中删除
     */
    @DeleteMapping(SearchConstant.URI_PREFIX + "/index/user")
    R<Void> deleteUserIndex(@RequestBody List<Long> ids);

    /**
     * 根据id列表将用户数据从ES中删除
     */
    @DeleteMapping(SearchConstant.URI_PREFIX + "/index/user")
    R<Void> deleteUserIndex(@RequestBody Long[] ids);
}
