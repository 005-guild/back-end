package com.fzj.pms.entity.pms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzj.pms.entity.enums.Constants;
import com.fzj.pms.entity.enums.PayStatus;
import com.fzj.pms.entity.security.Base;
import com.fzj.pms.entity.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "pays_users")
@SQLDelete(sql = "update pays_users set delete_flag="+ Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class PayUser extends Base {

    @ManyToOne
    @JoinColumn(name = "pay_id")
    private Pay pay;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate;

    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadLine;

    private String payName;

    private BigDecimal money;
}
