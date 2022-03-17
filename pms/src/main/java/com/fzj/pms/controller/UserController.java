package com.fzj.pms.controller;

import com.fzj.pms.entity.dto.UserDto;
import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.entity.security.User;
import com.fzj.pms.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public Result addUser(@RequestBody User user){
        return Objects.isNull(userService.registerUser(user))?Result.failure("添加用户失败"): Result.success();
    }

    /**
     * 编辑用户
     * @param user
     * @return
     */
    @PutMapping
    public Result editUser(@RequestBody User user){
        Boolean success = userService.updateUserInfo(user);
        return success?Result.success(): Result.failure("用户名已存在");
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping
    public Result deleteUser(@RequestParam("userId") Long userId){
        userService.lockUser(userId);
        return Result.success();
    }

    /**
     * 获取用户列表
     * @param parm
     * @return
     */
    @GetMapping("/list")
    public Result getUserList(){
        List<UserDto> List = userService.findAllList();
        return Result.success(List);
    }

}
