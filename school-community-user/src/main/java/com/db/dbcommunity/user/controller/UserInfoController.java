package com.db.dbcommunity.user.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.UserConstant;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.user.model.dto.UserAuthDTO;
import com.db.dbcommunity.user.model.vo.UserBasicInfoVO;
import com.db.dbcommunity.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class UserInfoController {

    @Resource
    private UserService userService;


    @GetMapping("/test")
    public String info() {
        return "test";
    }

    /**
     * 获取用户登录时的验证信息
     */
    @GetMapping("/userAuth")
    public R<UserAuthDTO> getUserAuthByUsername(String username) {
        UserAuthDTO userAuthDTO = userService.getUserAuthByAccount(username);
        return R.success(userAuthDTO);
    }

    /**
     * 获取用户自身的基本信息
     */
    @GetMapping("/basic")
    public R<UserBasicInfoVO> getSelfBasicInfo() {
        UserBasicInfoVO vo = userService.getUserBasicInfoById(UserContext.getCurrentUserId());
        return R.success(vo);
    }

    /**
     * 根据用户id获取用户的基本信息
     */
    @GetMapping("/basic/{userId}")
    public R<UserBasicInfoVO> getUserBasicInfo(@PathVariable Long userId) {
        UserBasicInfoVO vo = userService.getUserBasicInfoById(userId);
        return R.success(vo);
    }



    /**
     * 修改密码
     */
    @PutMapping("/password")
    public R<Void> updatePassword(@RequestBody Map<String, String> body) {
        return userService.updatePassword(body.get("oldPwd"), body.get("newPwd"), UserContext.getCurrentUserId()) ? R.success() : R.failed();
    }

    /**
     * 根据用户id获取头像
     */
    @GetMapping("/avatar")
    public R<SingleKeyVO> getUserAvatar(@RequestParam Long userId) {
        return R.success(new SingleKeyVO(userService.getAvatarByUserId(userId)));
    }


}
