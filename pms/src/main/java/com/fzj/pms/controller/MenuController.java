package com.fzj.pms.controller;

import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.entity.security.Menu;
import com.fzj.pms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询全部用户列表
     * @return
     */
    @GetMapping("/list")
    public Result getMenuList(){
        List<Menu> menuList = menuService.findAllByMenuTree();
        return Result.success(menuList);
    }

    /**
     * 新增用户列表
     * @return
     */
    @PostMapping
    public Result createMenu(@RequestBody Menu menu){
        return Result.success(menuService.create(menu));
    }

    /**
     * 编辑菜单
     * @return
     */
    @PutMapping
    public Result editMenu(@RequestBody Menu menu){
        menuService.update(menu);
        return  Result.success();
    }

    /**
     * 删除菜单
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteMenu(@PathVariable("id")Long id){
        menuService.delete(id);
        return Result.success();
    }

    /**
     * 获取上级菜单
     * @return
     */
    @GetMapping("/parent")
    public Result getParentMenu(){
        return Result.success(menuService.getCurrMenuTree());
    }

}
