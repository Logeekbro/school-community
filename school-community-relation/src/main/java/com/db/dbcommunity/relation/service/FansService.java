package com.db.dbcommunity.relation.service;

import com.db.dbcommunity.common.util.MyPage;

public interface FansService {

    MyPage<String> getFansList(Long currentUserId, Long current, Short size);

    Long getFansCountByUserId(Long userId);
}
