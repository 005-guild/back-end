package com.fzj.pms.controller;

import com.fzj.pms.entity.parms.ParkParm;
import com.fzj.pms.entity.pms.Park;
import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.service.ParkService;
import com.fzj.pms.service.UserService;
import com.fzj.pms.utils.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parkList")
public class ParkController {

    @Autowired
    private ParkService parkService;

    @Autowired
    private UserService userService;

    /**
     * 查询列表
     */
    @GetMapping("/list")
    public Result getList(Park park){
        return  Result.success(parkService.search(park, park.getPageSize(), park.getCurrentPage()));
    }

    /**
     * 新增车位
     */
    @PostMapping
    public Result add(@RequestBody ParkParm parkParm){
        String username = parkParm.getUsername();
        Park park = RequestUtil.ParmToEntity(parkParm);
        if(StringUtils.isNotBlank(username)){
            if(userService.getUserByUsername(username).isPresent()){
                park.setUser(userService.getUserByUsername(username).get());
                return Result.success(parkService.create(park));
            }else{
                return Result.failure("用户名错误");
            }
        }else{
            return Result.success(parkService.create(park));
        }
    }

    /**
     * 编辑车位
     */
    @PutMapping
    public Result edit(@RequestBody ParkParm parkParm){
        String username = parkParm.getUsername();
        Park park = RequestUtil.ParmToEntity(parkParm);
        if(StringUtils.isNotBlank(username)){
            if(userService.getUserByUsername(username).isPresent()){
                park.setUser(userService.getUserByUsername(username).get());
                parkService.update(park);
                return Result.success();
            }else{
                return Result.failure("用户名错误");
            }
        }else {
            parkService.update(park);
            return Result.success();
        }
    }

    /**
     * 删除车位
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long parkId){
        parkService.delete(parkId);
        return Result.success();
    }

}
