package com.fzj.pms.entity.pms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzj.pms.entity.enums.Constants;
import com.fzj.pms.entity.enums.ParkType;
import com.fzj.pms.entity.enums.UseStatus;
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
import java.util.Date;

@Data
@Table(name = "t_park")
@Entity
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "update t_park set delete_flag="+ Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class Park extends Base{

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

    @Override
    public String toString() {
        return "Park{" +
                "user=" + user +
                ", position='" + position + '\'' +
                ", parkType=" + parkType +
                ", expireDate=" + expireDate +
                ", useStatus=" + useStatus +
                '}';
    }

    public interface Save {
    }
}
