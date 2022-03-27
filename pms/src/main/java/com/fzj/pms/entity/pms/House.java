package com.fzj.pms.entity.pms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fzj.pms.entity.enums.Constants;
import com.fzj.pms.entity.enums.Storey;
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

@Data
@Table(name = "t_house")
@Entity
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "update t_house set delete_flag="+Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class House extends Base {

    /**
     * FetchType 有两种 方式  默认Lazy 懒加载 如果你用到的数据很急 就要用急加载 Eager
     * optional 属性是定义该关联类对是否必须存在，值为false时，
     * 关联类双方都必须存在，如果关系被维护端不存在，查询的结果为null。
     * 值为true 时, 关系被维护端可以不存在，查询的结果仍然会返回关系维护端，
     * 在关系维护端中指向关系被维护端的属性为null。
     * optional 属性的默认值是true。
     */

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String building;

    private String unit;

    private String position;

    /**
     * EnumType:  ORDINAL 枚举序数  默认选项（int）。eg:TEACHER 数据库存储的是 0
     *            STRING：枚举名称       (String)。eg:TEACHER 数据库存储的是 "TEACHER"
     */
//    @Enumerated(EnumType.STRING)
//    private Storey storey;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus;

    public interface Save {
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }

    private String username;
}
