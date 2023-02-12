package com.db.dbcommunity.common.constant;

import sun.awt.HKSCS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface GlobalConstant {
    String URL_PERM_ROLES_KEY = "system:perm_roles_rule:url:";
    Integer STATUS_ON = 0;
    Integer STATUS_OFF = 1;
    String USER_DEFAULT_PASSWORD = "123456";
    Long ROOT_MENU_ID = -1L;
    String ADMIN_URL_PERM = "%s:/%s%s";
    String REMOTE_ADDR = "X-Real-IP";
    Set<String> ACCEPT_IMG_TYPE = new HashSet<>(Arrays.asList("png", "jpg", "jpeg"));
}
