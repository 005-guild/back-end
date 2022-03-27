package com.fzj.pms.service;

import com.fzj.pms.entity.dto.UserDto;
import com.fzj.pms.entity.security.User;
import com.fzj.pms.entity.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * 登录验证
     *
     * @return
     */
    Optional<User> findByUserNameAndPassword(String username,String password);

    Optional<User> findById(Long id);

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
    boolean lockUser(Long id);

    /**
     * 删除用户
     *
     * @param id
     */
    boolean deleteUser(long id);

    /**
     * 根据用户名查询用户
     *
     * @param Username
     * @return
     */
    Optional<User> getUserByUsername(String Username);

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
    List<UserDto> listSearch(User user,int pageSize,int curentPage);

    /**
     * 模糊搜索
     * @param user
     * @return
     */
    Page<User> pageSearch(User user,int pageSize,int currentPage);

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
