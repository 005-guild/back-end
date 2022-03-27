package com.fzj.pms.entity.pms;

import com.fzj.pms.entity.enums.Constants;
import com.fzj.pms.entity.enums.RepairsStatus;
import com.fzj.pms.entity.security.Base;
import com.fzj.pms.entity.security.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_repairs")
@SQLDelete(sql = "update t_repairs set delete_flag="+Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class Repairs extends Base {

    @NotNull(message = "报修人不能为空")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @NotBlank(message = "报修项目不能为空")
//    private String repairsType;

    private String remark;

    @NotBlank(message = "联系人不能为空")
    private String linkman;

    @NotBlank(message = "联系电话不能为空")
    private String linkPhone;

    @NotBlank(message = "联系地址不能为空")
    private String linkAddress;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date repairsDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finishDate;

//    private String repairsBillNo;

//    private BigDecimal repairsPrice;

    @Enumerated(EnumType.STRING)
    private RepairsStatus repairsStatus;
}
