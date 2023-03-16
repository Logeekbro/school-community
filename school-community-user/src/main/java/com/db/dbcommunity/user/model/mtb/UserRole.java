package com.db.dbcommunity.user.model.mtb;

import com.db.dbcommunity.common.model.mtb.MiddleTable;
import org.springframework.stereotype.Component;

@Component
public class UserRole implements MiddleTable {
    @Override
    public String tableName() {
        return "user_role";
    }

    @Override
    public String firstFiledName() {
        return "user_id";
    }

    @Override
    public String secondFiledName() {
        return "role_id";
    }
}
