package fun.implementsstudio.mathforhim.config;

import fun.implementsstudio.mathforhim.entity.MemberEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@EqualsAndHashCode
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService dbUserLoginService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        log.info("authenticate invoke");
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) auth;
        String username = String.valueOf(authenticationToken.getName());
        MemberEntity member = null;
        try {
            UserDetails userDetails = dbUserLoginService.loadUserByUsername(username);
            if (userDetails == null) {
                throw new UsernameNotFoundException("用户名未找到");
            } else if (!userDetails.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException("验证过期");
            } else if (!userDetails.isAccountNonExpired()) {
                throw new AccountExpiredException("账户过期");
            } else if (!userDetails.isEnabled()) {
                throw new DisabledException("账户不可使用");
            } else if (!userDetails.isAccountNonLocked()) {
                throw new LockedException("账户已锁定");
            }
            UsernamePasswordAuthenticationToken authenticationResult = new UsernamePasswordAuthenticationToken(
                    userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            // 设置用户详细信息，用于记录ip、sessionId、证书序列号等值
            authenticationResult.setDetails(authenticationToken.getDetails());
            // 设置权限信息列表，默认是 GrantedAuthority 接口的一些实现类，通常是代表权限信息的一系列字符串
            SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}