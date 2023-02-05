package com.db.dbcommunity.auth.security.details.user;

import com.db.dbcommunity.auth.common.enums.PasswordEncoderTypeEnum;
import com.db.dbcommunity.auth.dto.UserAuthDTO;
import com.db.dbcommunity.auth.feign.UserFeignClient;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.api.ResultCode;
import com.db.dbcommunity.common.constant.GlobalConstant;
import com.db.dbcommunity.common.exception.ApiAsserts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service("communityUserDetailsService")
@Slf4j
public class CommunityUserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        R<UserAuthDTO> result = userFeignClient.getUserAuthByUsername(username);
        CommunityUserDetails userDetails = null;
        if (R.success().getCode().equals(result.getCode())) {
            UserAuthDTO user = result.getData();
            if (null != user) {
                userDetails = CommunityUserDetails.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .authorities(handleRoles(user.getRoles()))
                        .enabled(Objects.equals(user.getStatus(), GlobalConstant.STATUS_ON))
                        .password(PasswordEncoderTypeEnum.BCRYPT.getPrefix() + user.getPassword())
                        .build();
            } else {
                throw new UsernameNotFoundException(ResultCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
            }
        }
        if (Objects.isNull(userDetails)) {
            //TODO 不能正确响应，需要修改
            ApiAsserts.fail("用户服务不可用，请稍后重试");
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }

    private Collection<SimpleGrantedAuthority> handleRoles(List<String> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(roles == null) return authorities;
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}

