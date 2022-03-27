package com.fzj.pms.entity.pms;

import com.fzj.pms.entity.enums.Constants;
import com.fzj.pms.entity.enums.PayStatus;
import com.fzj.pms.entity.security.Base;
import com.fzj.pms.entity.security.Menu;
import com.fzj.pms.entity.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_pay")
@SQLDelete(sql = "update t_pay set delete_flag="+ Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class Pay extends Base {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pays_user",
            joinColumns = {@JoinColumn(name = "pay_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")})
    private Set<User> users;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deadLine;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finishDate;

    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

}
