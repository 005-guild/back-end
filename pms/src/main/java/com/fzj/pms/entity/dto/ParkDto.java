package com.fzj.pms.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzj.pms.entity.enums.ParkType;
import com.fzj.pms.entity.enums.UseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ParkDto {

    private Long id;

    private String username;

    private UseStatus useStatus;

    @JsonFormat(pattern="yyyy-MM-dd",locale = "zh", timezone = "GMT+8")
    private Date expireDate;

    private ParkType parkType;

    private String position;
}
