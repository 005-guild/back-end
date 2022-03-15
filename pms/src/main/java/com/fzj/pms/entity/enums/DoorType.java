package com.fzj.pms.entity.enums;

import lombok.Getter;

@Getter
public enum DoorType {

    QR("二维码"),
    NFC("门禁卡");

    private String name;

    DoorType(String name){
        this.name=name;
    }

}
