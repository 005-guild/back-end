package com.fzj.pms.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fzj.pms.entity.enums.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "t_menu")
@SQLDelete(sql = "update t_menu set delete_flag="+Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class Menu extends Base {

    private Long pid;

    private String parentName;

    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @NotBlank(message = "权限字段不能为空")
    private String menuCode;

    private String pathName;

    @NotBlank(message = "url 不能为空")
    private String path;

    private String type;

    @NotBlank(message = "图标不能为空")
    private String icon;

    @NotBlank(message = "组件不能为空")
    private String component;

    private Long orderNum;

    private String remark;

//    //@Column(columnDefinition="bigint default 0",nullable = false)
//    private Long pid;

    @ManyToMany(mappedBy = "menus")
    @JsonIgnore
    private Set<Role> roles;

    @Transient
    private List<Menu> children = new ArrayList<>();
}
