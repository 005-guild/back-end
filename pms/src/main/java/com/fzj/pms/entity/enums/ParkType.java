package com.fzj.pms.entity.enums;

import lombok.Getter;

@Getter
public enum ParkType {

    SMALL_CAR("小车"),
    BIG_CAR("大车");

    private String name;

    ParkType(String name){
        this.name=name;
    }
}
