package com.db.dbcommunity.common.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.db.dbcommunity.common.constant.AuthConstant;
import com.db.dbcommunity.common.constant.GlobalConstant;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

public class UserContext {


    public static Long getCurrentUserId() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AuthConstant.JWT_PAYLOAD_KEY);
        if (!StringUtils.hasText(token)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(URLDecoder.decode(token, "utf-8"));
            if (Objects.isNull(jsonObject)) {
                return null;
            }
            return jsonObject.getLong("userId");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return -2L;
    }

    public static String getCurrentUserJti() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(AuthConstant.JWT_PAYLOAD_KEY);
        if (!StringUtils.hasText(token)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(URLDecoder.decode(token, "utf-8"));
            if (Objects.isNull(jsonObject)) {
                return null;
            }
            return jsonObject.getString("jti");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getRemoteAddr() {
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(GlobalConstant.REMOTE_ADDR);
    }
}
