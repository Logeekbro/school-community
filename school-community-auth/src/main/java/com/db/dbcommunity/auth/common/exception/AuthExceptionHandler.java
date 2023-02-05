package com.db.dbcommunity.auth.common.exception;


import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(-1)
public class AuthExceptionHandler {

    /**
     * 用户不存在
     *
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public R handleUsernameNotFoundException(UsernameNotFoundException e) {
        return R.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
    }

    /**
     * 用户名或密码异常
     *
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InvalidGrantException.class)
    public R handleInvalidGrantException(InvalidGrantException e) {
        e.printStackTrace();
        return R.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
    }

    /**
     * 用户名或密码异常
     *
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidClientException.class)
    public R handleInvalidGrantException(InvalidClientException e) {
        return R.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
    }


    /**
     * 账户异常(禁用、锁定、过期)
     *
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public R handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return R.failed(e.getMessage());
    }

    /**
     * token 无效或已过期
     *
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidTokenException.class})
    public R handleInvalidTokenExceptionException(InvalidTokenException e) {
        return R.failed(e.getMessage());
    }

    /**
     * token 无效或已过期
     *
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NoSuchClientException.class})
    public R noSuchClientException(NoSuchClientException e) {
        return R.failed(e.getMessage());
    }


}
