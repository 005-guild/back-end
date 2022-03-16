package com.fzj.pms.security;

import com.fzj.pms.cache.TokenCache;
import com.fzj.pms.entity.enums.RestCode;
import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.entity.security.JwtToken;
import com.fzj.pms.entity.security.User;
import com.fzj.pms.exception.SystemErrorException;
import com.fzj.pms.service.UserService;
import com.fzj.pms.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private UserService userService;

    @Autowired
    private TokenCache tokenCache;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        if(req.getServletPath().equals("/auth/token")){
            chain.doFilter(req, res);
            return;
        }

        String token = getToken(req);

        String username=null;
        if(!StringUtils.isEmpty(token)){
             username = tokenCache.getUsername(new JwtToken().setAccessToken(token));
             if(StringUtils.isBlank(username)){
                 ResponseUtil.out(res, Result.failure(RestCode.TOKEN_EXPIRE));
                 return;
             }
        }

        if(StringUtils.isNotBlank(username)&&Objects.isNull(SecurityContextHolder.getContext().getAuthentication())){
            //数据库查询当前用户信息
            User user = userService.getUserByUsername(username)
                    .orElseThrow(() -> new SystemErrorException("用户异常"));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities()));
        }
        chain.doFilter(req, res);
    }

    /**
     * 从请求头重获取token
     * @param request
     * @return
     */
    private static String getToken(HttpServletRequest request){
        String header = request.getHeader(TOKEN_HEADER);
        if(StringUtils.isNotBlank(header) && header.startsWith(TOKEN_PREFIX)){
            return  StringUtils.removeStart(header,TOKEN_PREFIX);
        }
        return null;
    }
}
