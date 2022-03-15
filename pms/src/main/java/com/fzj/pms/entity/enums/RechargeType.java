package com.fzj.pms.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RechargeType {
    WECHART("微信支付"),
    ALIPAY("支付宝"),
    CASH("现金"),
    CARD("银行卡");

    private String name;

}
