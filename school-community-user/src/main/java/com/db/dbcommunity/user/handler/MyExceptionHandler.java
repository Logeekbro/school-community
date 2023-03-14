package com.db.dbcommunity.user.handler;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.api.ResultCode;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(basePackages = {"com.db.dbcommunity"})
public class MyExceptionHandler {

    /**
     * 唯一索引重复异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public R<Map<String, Object>> duplicateKeyExceptionHandler(DuplicateKeyException ex) {
        ex.printStackTrace();
        return R.failed(ResultCode.KEY_EXIST);
    }
}
