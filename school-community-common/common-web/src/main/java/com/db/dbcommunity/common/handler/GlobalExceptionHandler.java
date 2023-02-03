package com.db.dbcommunity.common.handler;


import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.api.ResultCode;
import com.db.dbcommunity.common.exception.ApiException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice(basePackages = {"com.db.dbcommunity"})
public class GlobalExceptionHandler {
    /**
     * 捕获自定义异常
     */
    @ExceptionHandler(value = ApiException.class)
    public R<Map<String, Object>> apiExceptionHandler(ApiException e) {
        if (e.getErrorCode() != null) {
            return R.failed(e.getErrorCode());
        }
        return R.failed(e.getMessage());
    }


    /**
     * 参数绑定失败抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public R<Map<String, Object>> bindExceptionHandler(BindException e) {
        FieldError error = e.getFieldError();
        if(error == null) {
            return R.failed(ResultCode.VALIDATE_FAILED);
        }
        return R.failed(error.getDefaultMessage());
    }

    /**
     * 参数校验失败抛出的异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    public R<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldError();
        ex.printStackTrace();
        if(error == null) {
            return R.failed(ResultCode.VALIDATE_FAILED);
        }
        else {
            return R.failed(error.getDefaultMessage());
        }
    }

    /**
     * Spring Security校验失败时抛出的异常
     */
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<Map<String, Object>> authenticationExceptionHandler(AuthenticationException ex) {
//        if(ex instanceof BadCredentialsException){
//            return ResponseEntity.failed(ex.getMessage());
//        }
//        return ResponseEntity.unauthorized(null);
//    }

    /**
     * 权限不足抛出的异常
     */
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<Map<String, Object>> accessDeniedExceptionHandler(AccessDeniedException ex) {
//        return ResponseEntity.forbidden(null);
//    }

    /**
     * 用户名不存在时抛出的异常
     */
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<Map<String, Object>> usernameNotFoundExceptionHandler(AccessDeniedException ex) {
//        return ResponseEntity.failed("用户不存在");
//    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Throwable.class)
    public R<Map<String, Object>> systemExceptionHandler(Throwable ex) {
        ex.printStackTrace();
        return R.failed("服务器异常");
    }

}
