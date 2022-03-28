package com.fzj.pms.entity.pms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzj.pms.entity.enums.Constants;
import com.fzj.pms.entity.enums.PayStatus;
import com.fzj.pms.entity.enums.UseStatus;
import com.fzj.pms.entity.security.Base;
import com.fzj.pms.entity.security.Menu;
import com.fzj.pms.entity.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_pay")
@SQLDelete(sql = "update t_pay set delete_flag="+ Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class Pay extends Base {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadLine;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date finishDate;

    private String payName;

    private String building;

    private String unit;

    private String position;

    private BigDecimal money;

    private int num;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus;
}
