package com.fzj.pms.entity.pms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fzj.pms.entity.enums.Constants;
import com.fzj.pms.entity.enums.UseStatus;
import com.fzj.pms.entity.security.Base;
import com.fzj.pms.entity.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_board")
@SQLDelete(sql = "update t_board set delete_flag="+Constants.DELETED+" where id= ?")
@Where(clause = "delete_flag="+ Constants.NORMEL)
public class Board extends Base {

    @NotNull(message = "发布公告人不能为空")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @JsonProperty(value = "boardContent")
    private String boardContent;

    @Pattern(regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$", message = "手机号码不符合规范")
    @NotBlank(message = "手机号码不能为空")
    private String linkPhone;

    @NotBlank(message = "名字不能为空")
    private String linkName;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus;

    @Override
    public String toString() {
        return "Board{" +
                "user=" + user +
                ", title='" + title + '\'' +
                ", boardContent='" + boardContent + '\'' +
                ", linkPhone='" + linkPhone + '\'' +
                ", linkName='" + linkName + '\'' +
                ", useStatus=" + useStatus +
                '}';
    }
}
