package com.fzj.pms.entity.security;

import com.fzj.pms.entity.enums.Constants;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name="t_role")
@Getter
@Setter
@SQLDelete(sql = "update t_role set delete_flag="+Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class Role extends Base {

    @Column(length = 25)
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @NotBlank(message = "描述不能为空")
    private String remark;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_menus",
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "id")})
    private Set<Menu> menus;

}
