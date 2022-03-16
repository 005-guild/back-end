package com.fzj.pms.cache;

import com.fzj.pms.entity.security.JwtToken;
import com.fzj.pms.entity.security.User;
import com.fzj.pms.utils.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class TokenCache {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final Map<String,JwtToken> userMap = new HashMap<>();

    public String getUsername(JwtToken jwtToken){
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        if(StringUtils.isNotBlank(username)){
            JwtToken token = userMap.get(username);
            return Objects.isNull(token)?null:token.getAccessToken().equals(jwtToken.getAccessToken())?username:null;
        }
        return null;
    }

    public JwtToken add(User user){
        JwtToken jwtToken = jwtTokenUtil.generateToken(user);
        return this.add(jwtToken);
    }

    public JwtToken add(JwtToken jwtToken){
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        userMap.put(username,jwtToken);
        return jwtToken;
    }

    public void remove(String username){
        userMap.remove(username);
    }

}
