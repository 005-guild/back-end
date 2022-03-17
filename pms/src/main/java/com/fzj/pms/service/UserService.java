package com.fzj.pms.service;

import com.fzj.pms.entity.dto.UserDto;
import com.fzj.pms.entity.security.User;
import com.fzj.pms.entity.vo.UserVo;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * 查询当前用户信息
     *
     * @return
     */
    //Optional<UserDto> getCurrUserInfo();

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    User registerUser(User user);

    /**
     * 锁定用户
     *
     * @param id
     */
    void lockUser(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param Username
     * @return
     */
    Optional<User> getUserByUsername(String Username);

    /**
     * 查询全部用户列表
     *
     * @return
     */
    List<UserDto> findAllList();

    /**
     * 查询全部用户列表
     *
     * @return
     */
    List<UserDto> findAllListSortCreateTime();

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Boolean updateUserInfo(User user);

    /**
     * 模糊搜索
     * @param user
     * @return
     */
    List<UserDto> search(User user);

    /**
     * 通过id 查询用户
     * @param id
     * @return
     */
    Optional<UserDto> findUser(Long id);

//    /**
//     * 更新密码
//     * @param userVo
//     */
//    int updatePassword(UserVo userVo);
//
//
//    /**
//     * 重值密码
//     * @param userId
//     */
//    void resetPassword(Long userId);
}
