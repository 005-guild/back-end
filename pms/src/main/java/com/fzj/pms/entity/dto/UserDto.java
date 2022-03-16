package com.fzj.pms.entity.dto;

import com.fzj.pms.entity.enums.UseStatus;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String phone;

    private String realName;

    private RoleDto role;

    private UseStatus useStatus;

}
