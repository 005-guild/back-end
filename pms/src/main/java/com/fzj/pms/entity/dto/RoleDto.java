package com.fzj.pms.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleDto {

    private Long id;

    private String name;

    private String remark;

    private int citeNum;

}
