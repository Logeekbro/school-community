package com.db.dbcommunity.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;


@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增填充创建时间、操作人
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
        this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class);
//        this.strictInsertFill(metaObject, "createBy", () -> UserContext.getCurrentUserId(), Long.class);
//        this.strictUpdateFill(metaObject, "updateBy", () -> UserContext.getCurrentUserId(), Long.class);
    }

    /**
     * 更新填充更新时间、操作人
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
//        this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class);
//        this.strictUpdateFill(metaObject, "updateBy", () -> UserContext.getCurrentUserId(), Long.class);
    }

}
