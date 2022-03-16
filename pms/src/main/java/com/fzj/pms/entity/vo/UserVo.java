package com.fzj.pms.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserVo {

    @NotNull(message = "用户id不能为空")
    public Long id;

    @NotBlank(message = "旧密码不能为空")
    public String oldPassword;

    @NotBlank(message = "新密码不能为空")
    public String newPassword;

}
