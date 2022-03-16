package com.fzj.pms.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fzj.pms.entity.enums.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @program: Menu
 * @description:
 * @author: fzy
 * @date: 2019/03/18 22:16:27
 **/
@Entity
@Getter
@Setter
@Table(name = "t_menu")
@ApiModel("菜单")
@SQLDelete(sql = "update t_menu set delete_flag="+ Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class Menu extends Base {

    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @NotBlank(message = "url 不能为空")
    private String path;

    @NotBlank(message = "图标不能为空")
    private String icon;

    @NotBlank(message = "组件不能为空")
    private String component;

    @Column(columnDefinition="bigint default 0",nullable = false)
    private Long pid;

    @ManyToMany(mappedBy = "menus")
    @JsonIgnore
    private Set<Role> roles;
}
