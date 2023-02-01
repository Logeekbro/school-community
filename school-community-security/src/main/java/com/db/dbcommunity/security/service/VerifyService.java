package com.db.dbcommunity.security.service;

import com.db.dbcommunity.security.model.dto.VerifyDTO;
import com.db.dbcommunity.security.model.vo.VerifyVO;

public interface VerifyService {
    VerifyVO startVerify();

    VerifyDTO getLoginVerifyCode(String verifyId);

    void loadVerifyWordFromDB();

    String handleVerify(String verifyId, String value);
}
