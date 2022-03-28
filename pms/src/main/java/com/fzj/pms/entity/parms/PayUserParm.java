package com.fzj.pms.entity.parms;

import com.fzj.pms.entity.enums.PayStatus;
import lombok.Data;

import java.util.Date;

@Data
public class PayUserParm extends ParmBase{

    private Date payDate;

    private PayStatus payStatus;
}
