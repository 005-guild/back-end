package com.fzj.pms.entity.parms;

import com.fzj.pms.entity.enums.UseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class BoardParm extends ParmBase{

    private String title;

    private String boardContent;

    private String linkPhone;

    private String linkName;

    private UseStatus useStatus;

    @Override
    public String toString() {
        return "BoardParm{" +
                "title='" + title + '\'' +
                ", boardContent='" + boardContent + '\'' +
                ", linkPhone='" + linkPhone + '\'' +
                ", linkName='" + linkName + '\'' +
                ", useStatus=" + useStatus +
                '}';
    }
}
