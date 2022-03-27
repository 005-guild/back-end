package com.fzj.pms.entity.parms;

import com.fzj.pms.entity.enums.ReplyStatus;
import com.fzj.pms.entity.security.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class OpinionParm extends ParmBase {

    private String opinionTitle;

    private String opinionContent;

    private Long replyId;

    private String replyContent;

    private Date replyDate;

    private ReplyStatus replyStatus;
}

