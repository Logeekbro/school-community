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
            //TODO ?????????????????????????????????
            ApiAsserts.fail("???????????????????????????????????????");
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("?????????????????????!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("?????????????????????!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("??????????????????!");
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

