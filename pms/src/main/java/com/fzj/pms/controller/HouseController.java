package com.fzj.pms.controller;

import com.fzj.pms.entity.pms.House;
import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.service.HouseService;
import com.fzj.pms.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/houseList")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public Result getList(House house){
        return Result.success(houseService.search(house, house.getPageSize(),house.getCurrentPage()));
    }

    @PostMapping
    public Result add(@RequestBody House house){
        if(StringUtils.isNotBlank(house.getUsername())){
            if(userService.getUserByUsername(house.getUsername()).isPresent()){
                house.setUser(userService.getUserByUsername(house.getUsername()).get());
                return Result.success(houseService.create(house));
            }else{
                return Result.failure("用户不存在");
            }
        }else{
            return Result.success(houseService.create(house));
        }
    }

    @PutMapping
    public Result edit(@RequestBody House house){
        if(StringUtils.isNotBlank(house.getUsername())){
            if(userService.getUserByUsername(house.getUsername()).isPresent()){
                house.setUser(userService.getUserByUsername(house.getUsername()).get());
                houseService.update(house);
                return Result.success();
            }else{
                return Result.failure("用户不存在");
            }
        }else{
            houseService.update(house);
            return Result.success();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id){
        houseService.delete(id);
        return Result.success();
    }
}
