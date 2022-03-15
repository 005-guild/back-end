package com.fzj.pms.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RepairsStatus {

     NOT("未完成"),
     ING("进行中"),
     YES("已完成");

    private String name;

}
