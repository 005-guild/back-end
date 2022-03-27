package com.fzj.pms.service;

import com.fzj.pms.entity.dto.MenuDto;
import com.fzj.pms.entity.security.Menu;

import java.util.List;
import java.util.Set;

public interface MenuService {

    /**
     * 查询全部用户列表
     * @return
     */
    List<Menu> findAllByMenuTree();


    /**
     * 查询当前用户菜单列表
     * @return
     */
    List<Menu> getCurrMenuTree();

    /**
     * 创建菜单
     * @param menu
     * @return
     */
    Menu create(Menu menu);

    /**
     * 更新菜单
     * @param menu
     */
    void update(Menu menu);

    /**
     * 删除id
     * @param id
     */
    void delete(Long id);



}
