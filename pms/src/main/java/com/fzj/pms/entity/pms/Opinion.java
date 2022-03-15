package com.fzj.pms.entity.pms;

import com.fzj.pms.entity.enums.ReplyStatus;
import com.fzj.pms.entity.security.Base;
import com.fzj.pms.entity.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "p_opinion")
public class Opinion extends Base {

    @NotNull(message = "意见发起人不能为空")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "内容不能为空")
    private String opinionContent;

    private int replyId;

    private String replyContent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date replyDate;

    @Enumerated(EnumType.STRING)
    private ReplyStatus replyStatus;
}
