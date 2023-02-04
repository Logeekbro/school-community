package com.db.dbcommunity.user.model.mtb;

import com.db.dbcommunity.common.model.mtb.MiddleTable;
import org.springframework.stereotype.Component;

@Component
public class RolePermission implements MiddleTable {
    @Override
    public String tableName() {
        return "role_permission";
    }

    @Override
    public String firstFiledName() {
        return "role_id";
    }

    @Override
    public String secondFiledName() {
        return "permission_id";
    }
}
