package com.fzj.pms.controller;

import com.fzj.pms.entity.parms.RepairsParm;
import com.fzj.pms.entity.pms.Repairs;
import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.service.RepairsService;
import com.fzj.pms.service.UserService;
import com.fzj.pms.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userRepair")
public class RepairsController {

    @Autowired
    private RepairsService repairsService;

    @Autowired
    private UserService userService;

    /**
     * 我的报修列表
     */
    @GetMapping("/myList")
    public Result getMyList(Repairs repairs,Long userId){
        if(userService.findById(userId).isPresent()){
            repairs.setUser(userService.findById(userId).get());
            return Result.success(repairsService.search(repairs,repairs.getPageSize(), repairs.getCurrentPage()));
        }else {
            return Result.failure("报修查询失败");
        }
    }

    /**
     * 报修列表(物业管理部人员)
     */
    @GetMapping("/list")
    public Result getList(Repairs repairs){
        return Result.success(repairsService.search(repairs, repairs.getPageSize(), repairs.getCurrentPage()));
    }

    /**
     * 新增
     */
    @PostMapping
    public Result add(@RequestBody RepairsParm repairsParm){
        Long userId = repairsParm.getUserId();
        if(userService.findById(userId).isPresent()){
            Repairs repairs = RequestUtil.ParmToEntity(repairsParm);
            repairs.setUser(userService.findById(userId).get());
            return Result.success(repairsService.create(repairs));
        }else{
            return Result.failure("报修失败");
        }
    }

    /**
     * 编辑
     */
    @PutMapping
    public Result edit(@RequestBody RepairsParm repairsParm){
        Repairs repairs = RequestUtil.ParmToEntity(repairsParm);
        repairsService.update(repairs);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long repairId){
        repairsService.delete(repairId);
        return Result.success();
    }
}
