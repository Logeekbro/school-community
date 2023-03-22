package com.db.dbcommunity.user.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.MyBeanUtil;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.user.model.dto.UserAuthDTO;
import com.db.dbcommunity.user.model.dto.UserDetailInfoDTO;
import com.db.dbcommunity.user.model.entity.User;
import com.db.dbcommunity.user.model.vo.UserDetailInfoVO;
import com.db.dbcommunity.user.model.vo.UserBasicInfoVO;
import com.db.dbcommunity.user.service.UserService;
import org.springframework.validation.annotation.Validated;
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

    /**
     * 根据用户id获取昵称
     */
    @GetMapping("/nickName")
    public R<SingleKeyVO> getUserNickName(@RequestParam Long userId) {
        return R.success(new SingleKeyVO(userService.getNickNameByUserId(userId)));
    }

    /**
     * 获取用户详细信息
     */
    @GetMapping("/detail")
    public R<UserDetailInfoVO> getUserDetailInfo() {
        User user = userService.getById(UserContext.getCurrentUserId());
        return R.success(MyBeanUtil.copyProps(user, UserDetailInfoVO.class));
    }

    /**
     * 修改用户详细信息
     */
    @PutMapping("/")
    public R<Void> updateUserDetailInfo(@RequestBody @Validated UserDetailInfoDTO vo) {
        vo.setUserId(UserContext.getCurrentUserId());
        return userService.updateDetailInfo(vo) ? R.success() : R.failed();
    }

    /**
     * 提供给服务内部调用的修改用户信息接口，不对数据进行校验
     */
    @PutMapping("/internal")
    public R<Void> updateUserDetailInfoInternal(@RequestBody UserDetailInfoDTO vo) {
        vo.setUserId(UserContext.getCurrentUserId());
        return userService.updateDetailInfo(vo) ? R.success() : R.failed();
    }
}
