package com.fzj.pms.entity.pms;

import com.fzj.pms.entity.security.Base;
import com.fzj.pms.entity.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="t_comment")
@EqualsAndHashCode(callSuper = true)
public class Comment extends Base {

    @NotNull(message = "评论发布人不能为空")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "评论所在公告栏不能为空")
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String commentContent;

    private String commentStatus;
}
