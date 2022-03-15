package com.fzj.pms.entity.pms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzj.pms.entity.enums.ParkType;
import com.fzj.pms.entity.enums.UseStatus;
import com.fzj.pms.entity.security.Base;
import com.fzj.pms.entity.security.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Table(name = "p_park")
@Entity
@EqualsAndHashCode(callSuper = true)
public class Park extends Base{

    @ApiModelProperty("用户")
    @NotNull(groups = {Base.Update.class, Park.Save.class},message = "用户不能为空")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "位置不能为空")
    private String position;

    @Enumerated(EnumType.STRING)
    private ParkType parkType;

    @JsonFormat(pattern="yyyy-MM-dd",locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus;

    public interface Save {
    }
}
