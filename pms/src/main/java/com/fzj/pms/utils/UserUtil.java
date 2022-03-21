package com.fzj.pms.utils;

import com.fzj.pms.entity.parms.UserParm;
import com.fzj.pms.entity.security.User;

public class UserUtil {

    public static User ParmsToUser(UserParm userParm){
        User user= new User();
        user.setUsername(userParm.getUserName());
        user.setPhone(userParm.getPhone());
        return user;
    }

}
