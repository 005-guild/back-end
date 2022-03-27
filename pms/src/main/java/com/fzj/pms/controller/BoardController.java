package com.fzj.pms.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzj.pms.entity.parms.BoardParm;
import com.fzj.pms.entity.pms.Board;
import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.service.BoardService;
import com.fzj.pms.service.UserService;
import com.fzj.pms.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sysNotice")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    /**
     * 查询列表
     */
    @GetMapping("/list")
    public Result getList(Board board){
        return Result.success(boardService.search(board,board.getPageSize(), board.getCurrentPage()));
    }

    @GetMapping("/find")
    public Result getById(Long id){
        if(id==null){
            return Result.failure("请选择公告");
        }
        if(boardService.findById(id).isPresent()){
            return Result.success(boardService.findById(id).get());
        }else{
            return Result.failure("查看公告失败");
        }
    }

    /**
     * 新增
     */
    @PostMapping
    public Result add(@RequestBody BoardParm boardParm){
        Long userId = boardParm.getUserId();
        System.out.println(boardParm);
        if(userService.findById(userId).isPresent()){
            Board board = RequestUtil.ParmToEntity(boardParm);
            board.setUser(userService.findById(userId).get());
            return Result.success(boardService.create(board));
        }else{
            return Result.failure("发布公告失败");
        }
    }

    /**
     * 编辑
     */
    @PutMapping
    public Result edit(@RequestBody BoardParm boardParm){
        Board board = RequestUtil.ParmToEntity(boardParm);
        boardService.update(board);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id){
        boardService.delete(id);
        return Result.success();
    }

}
