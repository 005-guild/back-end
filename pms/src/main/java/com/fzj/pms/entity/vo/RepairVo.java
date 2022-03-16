package com.fzj.pms.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class RepairVo {

    @NotNull(message = "id 不能为空")
    private Long id;

    private BigDecimal RepairsPrice;

    private String repairsBillNo;
}
