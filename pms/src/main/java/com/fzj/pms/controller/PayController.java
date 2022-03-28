package com.fzj.pms.controller;

import com.fzj.pms.entity.enums.PayStatus;
import com.fzj.pms.entity.enums.UseStatus;
import com.fzj.pms.entity.parms.PayUserParm;
import com.fzj.pms.entity.pms.*;
import com.fzj.pms.entity.rest.Result;
import com.fzj.pms.service.HouseService;
import com.fzj.pms.service.PayService;
import com.fzj.pms.service.PayUserService;
import com.fzj.pms.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/payProject")
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @Autowired
    private PayUserService payUserService;

    @GetMapping("/list")
    public Result getList(Pay pay){
        return  Result.success(payService.search(pay,pay.getPageSize(),pay.getCurrentPage()));
    }

    @GetMapping("/myList")
    public Result getMyList(PayUser payUser, Long userId){
        if(userService.findById(userId).isPresent()){
            payUser.setUser(userService.findById(userId).get());
            return Result.success(payUserService.search(payUser, payUser.getPageSize(), payUser.getCurrentPage()));
        }else{
            return Result.failure("缴费项目查询失败");
        }
    }

    @PostMapping
    public Result add(@RequestBody Pay pay){
        if(pay.getUseStatus()== UseStatus.DISABLED){
            if(houseSearch(pay)!=null){
                String hs = houseSearch(pay);
                int num = 0;
                switch (hs) {
                    case "Building":
                        if (houseService.findByBuilding(pay.getBuilding()).isEmpty()) {
                            return Result.failure("楼栋输入错误");
                        }else{
                            List<House> list = houseService.findByBuilding(pay.getBuilding());
                            for (House house:
                                 list) {
                                if(house.getUser()!=null){
                                    num++;
                                }
                            }
                        }
                        break;
                    case "Unit":
                        if (houseService.findByBuildingAndUnit(pay.getBuilding(), pay.getUnit()).isEmpty()) {
                            return Result.failure("楼栋或单元输入错误");
                        }else{
                            List<House> list = houseService.findByBuildingAndUnit(pay.getBuilding(), pay.getUnit());
                            for (House house:
                                    list) {
                                if(house.getUser()!=null){
                                    num++;
                                }
                            }
                        }
                        break;
                    case "Position":
                        if (houseService.findByBuildingAndUnitAndPosition(pay.getBuilding(), pay.getUnit(), pay.getPosition()).isEmpty()) {
                            return Result.failure("楼栋、单元或者房屋编号输入错误");
                        }else{
                            num=1;
                        }
                        break;
                    default:
                        return Result.failure("系统出错了");
                }
                pay.setNum(num);
                return Result.success(payService.create(pay));
            }else{
                return Result.failure("无法获取房屋信息");
            }
        }else if(pay.getUseStatus()==UseStatus.ENABLED){
            return Result.failure("系统出错");
        }else{
            return Result.failure("创建缴费项目失败");
        }
    }

    @PutMapping
    public Result edit(@RequestBody Pay pay){
        if(pay.getUseStatus()==UseStatus.DISABLED){
            payService.update(pay);
            return Result.success();
        }else{
            if(houseSearch(pay)!=null){
                String hs = houseSearch(pay);
                switch (hs) {
                    case "Building":
                        List<House> list = houseService.findByBuilding(pay.getBuilding());
                        for (House house:
                                list) {
                            if(house.getUser()!=null){
                                PayUser payUser = new PayUser();
                                payUser.setPay(pay);
                                payUser.setUser(house.getUser());
                                payUser.setPayStatus(PayStatus.UNPAID);
                                payUser.setDeadLine(pay.getDeadLine());
                                payUser.setPayName(pay.getPayName());
                                payUser.setMoney(pay.getMoney());
                                payUserService.create(payUser);
                            }
                        }
                        break;
                    case "Unit":
                        List<House> unitList = houseService.findByBuildingAndUnit(pay.getBuilding(), pay.getUnit());
                        for (House house:
                                unitList) {
                            if(house.getUser()!=null){
                                PayUser payUser = new PayUser();
                                payUser.setPay(pay);
                                payUser.setUser(house.getUser());
                                payUser.setPayStatus(PayStatus.UNPAID);
                                payUser.setDeadLine(pay.getDeadLine());
                                payUser.setPayName(pay.getPayName());
                                payUser.setMoney(pay.getMoney());
                                payUserService.create(payUser);
                            }
                        }
                        break;
                    case "Position":
                        House house = houseService.findByBuildingAndUnitAndPosition(pay.getBuilding(), pay.getUnit(), pay.getPosition()).get();
                        PayUser payUser = new PayUser();
                        payUser.setPay(pay);
                        payUser.setUser(house.getUser());
                        payUser.setDeadLine(pay.getDeadLine());
                        payUser.setPayStatus(PayStatus.UNPAID);
                        payUser.setPayName(pay.getPayName());
                        payUser.setMoney(pay.getMoney());
                        payUserService.create(payUser);
                        break;
                    default:
                        return Result.failure("系统出错了");
                }
                payService.update(pay);
                return Result.success();
            }else{
                return Result.failure("无法获取房屋信息");
            }
        }
    }

    @PutMapping("/pay")
    public Result pay(@RequestBody PayUser payUser){
        payUser.setPayDate(new Date());
        System.out.println(payUser);
        payUserService.update(payUser);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id")Long id){
        //payUserService.deleteAllByPid(id);
        payService.delete(id);
        return Result.success();
    }

    @DeleteMapping("/pay/{id}")
    public Result deletePay(@PathVariable("id")Long id){
        //payUserService.deleteAllByPid(id);
        payUserService.delete(id);
        //payService.delete(id);
        return Result.success();
    }

    public String houseSearch(Pay pay){
        if(StringUtils.isNotBlank(pay.getBuilding())){
            if(StringUtils.isNotBlank(pay.getUnit())){
                if(StringUtils.isNotBlank(pay.getPosition())){
                    return "Position";
                }else{
                    return "Unit";
                }
            }else{
                return "Building";
            }
        }else{
            return null;
        }
    }
}
