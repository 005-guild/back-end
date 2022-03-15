package com.fzj.pms.entity.pms;

import com.fzj.pms.entity.enums.MessageStatus;
import com.fzj.pms.entity.security.Base;
import com.fzj.pms.entity.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="p_message")
public class Message extends Base {

    @NotNull(message = "发件人不能为空")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "接收人不能为空")
    private int receiver;

    @NotBlank(message = "内容不能为空")
    private String messageContent;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;
}
