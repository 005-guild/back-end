package com.fzj.pms.service;

import com.fzj.pms.entity.dto.RoleDto;
import com.fzj.pms.entity.security.Role;
import com.fzj.pms.entity.security.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface RoleService {

    /**
     * 创建角色
     * @param role
     * @return
     */
    Role create(Role role);

    /**
     * 更新角色
     * @param role
     */
    void update(Role role);

    /**
     * 根据id 删除 菜单
     * @param id
     */
    void delete(Long id);

    /**
     * 查询全部的角色
     * @return
     */
    List<RoleDto> findAll();

    /**
     * 批量删除角色
     * @return
     */
    int batchDelete(Set<Long> ids);

    /**
     * 角色分页搜索
     * @return
     */
    Page<Role> searchByName(String name,int pageSize,int currentPage);

//    /**
//     * 角色模糊搜索
//     * @param role
//     * @return
//     */
//    List<RoleDto> search(String role);

    /**
     * 根据id 查询用户
     * @param id
     * @return
     */
    Role findOne(Long id);
}
