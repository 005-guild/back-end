package com.fzj.pms.controller;

import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.entity.security.Menu;
import com.fzj.pms.entity.security.Role;
import com.fzj.pms.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public Result create(@Validated @RequestBody Role role){
        roleService.create(role);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Role role){
        roleService.update(role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteRole(@PathVariable Long id){
        roleService.delete(id);
        return Result.success();
    }

//    @GetMapping
//    public Result findAll(){
//        return Result.success(roleService.findAll());
//    }

//    @DeleteMapping("/batchDelete")
//    public Result batchDelete(@RequestBody Set<Long> ids){
//        roleService.batchDelete(ids);
//        return Result.success();
//    }

//    @GetMapping("/search")
//    public Result search(@RequestParam("name") String name){
//        return Result.success(roleService.search(name));
//    }

    @GetMapping("/list")
    public Result getRoleList(@RequestParam("name") String name,@RequestParam("pageSize") int pageSize,@RequestParam("currentPage") int currentPage){
        Page<Role> rolePageList = roleService.searchByName(name, pageSize, currentPage);
        return Result.success("查询成功",rolePageList);
    }


//    @GetMapping("/{id:\\d+}")
//    public Result findOne(@PathVariable Long id){
//        Role role = roleService.findOne(id);
//        return Result.success(role);
//    }

}
