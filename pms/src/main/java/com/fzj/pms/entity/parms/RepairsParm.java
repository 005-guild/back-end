package com.fzj.pms.entity.parms;

import com.fzj.pms.entity.enums.RepairsStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class RepairsParm extends ParmBase{

//    private String repairsType;

    private String remark;

    private String linkman;

    private String linkPhone;

    private String linkAddress;

//    private Date repairsDate;

    private Date finishDate;

//    private String repairsBillNo;

//    private BigDecimal repairsPrice;

    private RepairsStatus repairsStatus;

}
