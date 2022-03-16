package com.fzj.pms.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class RepairsReportDto {

    private Long id;

    private String realName;

    private String repairsBillNo;

    private BigDecimal repairsPrice;

    private String repairsType;

    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date repairsDate;

    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date finishDate;

}
