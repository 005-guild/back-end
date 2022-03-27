package com.fzj.pms.entity.rest;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResult implements Serializable {

    private Long userId;

    private String token;
    //private Long expireTime;
}
