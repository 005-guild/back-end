package com.fzj.pms.security;

import com.fzj.pms.cache.TokenCache;
import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.entity.security.JwtToken;
import com.fzj.pms.entity.security.User;
import com.fzj.pms.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private TokenCache tokenCache;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JwtToken jwtToken = tokenCache.add(principal);
        ResponseUtil.out(response, Result.success(jwtToken));
    }
}
