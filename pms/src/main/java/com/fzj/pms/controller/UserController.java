package com.fzj.pms.controller;

import com.fzj.pms.entity.dto.UserDto;
import com.fzj.pms.entity.parms.UserParm;
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
        System.out.println(userId);
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
        Page<User> userPageList = userService.pageSearch(user,userParm.getPageSize(), userParm.getCurentPage());
        return Result.success("查询成功",userPageList);
    }

}
