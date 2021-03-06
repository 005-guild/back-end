package com.fzj.pms.entity.security;

import com.fzj.pms.entity.enums.Constants;
import com.fzj.pms.entity.enums.UseStatus;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="t_user")
@SQLDelete(sql = "update t_user set delete_flag="+Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class User extends Base {

    @NotBlank(message = "用户名不能为空")
    //@Column(unique = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String sex;

    @Email(message = "邮箱不符合规则")
    private String email;

    @Pattern(regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$", message = "手机号码不符合规范")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @NotBlank(message = "名字不能为空")
    private String realName;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus= UseStatus.ENABLED;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName="id",nullable = true)
    private Role role;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", useStatus=" + useStatus +
                ", role=" + role +
                '}';
    }

    //    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Sets.newHashSet(new SimpleGrantedAuthority(role.getName()));
//    }
//
//    @Override
//    public String getUsername() {
//        return this.username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
    //@Override
    public boolean isEnabled() {
        return useStatus.equals(UseStatus.ENABLED);
    }

}
