package com.fzj.pms.entity.pms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzj.pms.entity.enums.Constants;
import com.fzj.pms.entity.enums.DoorType;
import com.fzj.pms.entity.enums.UseStatus;
import com.fzj.pms.entity.security.Base;
import com.fzj.pms.entity.security.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Table(name = "t_door")
@Entity
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "update t_door set delete_flag="+Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class Door extends Base {

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus= UseStatus.ENABLED;

    @Enumerated(EnumType.STRING)
    private DoorType doorType;

    @NotNull(groups = {Update.class, Save.class},message = "用户不能为空")
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonFormat(pattern="yyyy-MM-dd",locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.DATE)
    private Date expireDate;

    public interface Save {
    }
}
