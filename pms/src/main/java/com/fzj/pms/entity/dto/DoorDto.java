package com.fzj.pms.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzj.pms.entity.enums.DoorType;
import com.fzj.pms.entity.enums.UseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoorDto implements Serializable{

    private Long id;

    private String username;

    private UseStatus useStatus;

    @JsonFormat(pattern="yyyy-MM-dd",locale = "zh", timezone = "GMT+8")
    private Date expireDate;

    private DoorType doorType;
}
