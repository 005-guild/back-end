package com.fzj.pms.controller;

import com.fzj.pms.cache.TokenCache;
import com.fzj.pms.dao.UserRepository;
import com.fzj.pms.entity.dto.UserDto;
import com.fzj.pms.entity.parms.LoginParm;
import com.fzj.pms.entity.parms.UserInfo;
import com.fzj.pms.entity.parms.UserParm;
import com.fzj.pms.entity.rest.LoginResult;
import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.entity.security.User;
import com.fzj.pms.service.UserService;
import com.fzj.pms.utils.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenCache tokenCache;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginParm parm){
        if(StringUtils.isEmpty(parm.getUsername()) || StringUtils.isEmpty(parm.getPassword())){
            return Result.failure("用户名、密码不能为空");
        }
        String password = DigestUtils.md5DigestAsHex(parm.getPassword().getBytes());
        Optional<User> userOptional=userService.findByUserNameAndPassword(parm.getUsername(),password);
        if(userOptional.isEmpty()){
            return Result.failure("用户名或者密码错误");
        }
        String token = tokenCache.add(userOptional.get()).getAccessToken();
        LoginResult loginResult=new LoginResult();
        loginResult.setUserId(userOptional.get().getId());
        loginResult.setToken(token);
        return Result.success(loginResult);
    }

    /**
     * 获取用户信息
     * @param user
     * @return
     */
    @GetMapping("/info")
    public Result getInfo(User user){
        if(userService.findById(user.getId()).isEmpty()){
            return Result.failure("用户不存在");
        }
        User loginUser = userService.findById(user.getId()).get();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(loginUser.getId());
        userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        userInfo.setName(loginUser.getUsername());
        return Result.success(userInfo);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public Result addUser(@Valid @RequestBody User user){
        return Objects.isNull(userService.registerUser(user))?Result.failure("添加用户失败"): Result.success("添加用户成功");
    }

    /**
     * 编辑用户
     * @param user
     * @return
     */
    @PutMapping
    public Result editUser(@Valid @RequestBody User user){
        Boolean success = userService.updateUserInfo(user);
        return success?Result.success(): Result.failure("用户名已存在");
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable("userId") Long userId){
        //System.out.println(userId);
        boolean re = userService.deleteUser(userId);
        if(re){
            return Result.success();
        }
        return Result.failure("删除用户失败");
    }

    /**
     * 查询获取用户列表
     * @param parm
     * @return
     */
    @GetMapping("/list")
    public Result getUserList(UserParm userParm){
        User user = UserUtil.ParmsToUser(userParm);
        //List<UserDto> userDtoList = userService.listSearch(user,userParm.getPageSize(), userParm.getCurentPage());
        Page<User> userPageList = userService.pageSearch(user,userParm.getPageSize(), userParm.getCurrentPage());
        return Result.success("查询成功",userPageList);
    }

}
