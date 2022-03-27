package com.fzj.pms.entity.parms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzj.pms.entity.enums.ParkType;
import com.fzj.pms.entity.enums.UseStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ParkParm extends ParmBase{

    private String position;

    private ParkType parkType;

    private Date expireDate;

    private UseStatus useStatus;

    private String username;
}
