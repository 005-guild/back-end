package com.fzj.pms.controller;

import com.fzj.pms.entity.enums.ReplyStatus;
import com.fzj.pms.entity.parms.OpinionParm;
import com.fzj.pms.entity.pms.Opinion;
import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.service.OpinionService;
import com.fzj.pms.service.UserService;
import com.fzj.pms.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userComplaint")
public class OpinionController {

    @Autowired
    private OpinionService opinionService;

    @Autowired
    private UserService userService;

    /**
     * 我的投诉
     * @return
     */
    @GetMapping("/myList")
    public Result getMyList(Opinion opinion,Long userId){
        if(userService.findById(userId).isPresent()){
            opinion.setUser(userService.findById(userId).get());
            return Result.success(opinionService.search(opinion, opinion.getPageSize(), opinion.getCurrentPage()));
        }else{
            return Result.failure("投诉查询失败");
        }
    }

    /**
     * 投诉列表
     */
    @GetMapping("/list")
    public Result getList(Opinion opinion){
        return Result.success(opinionService.search(opinion, opinion.getPageSize(), opinion.getCurrentPage()));
    }

    /**
     * 新增投诉
     */
    @PostMapping
    public Result add(@RequestBody OpinionParm opinionParm){
        Long userId = opinionParm.getUserId();
        if(userService.findById(userId).isPresent()){
            Opinion opinion= RequestUtil.ParmToEntity(opinionParm);
            opinion.setUser(userService.findById(userId).get());
            //opinion.setReplyStatus(ReplyStatus.UNREPLIED);
            return Result.success(opinionService.create(opinion));
        }else{
            return Result.failure("投诉失败");
        }
    }

    /**
     * 编辑投诉
     */
    @PutMapping
    public Result edit(@RequestBody OpinionParm opinionParm){
        Opinion opinion= RequestUtil.ParmToEntity(opinionParm);
        opinionService.update(opinion);
        return Result.success();
    }

    /**
     * 删除
     * @param complaintId
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id")Long complaintId){
        opinionService.delete(complaintId);
        return Result.success();
    }

}
