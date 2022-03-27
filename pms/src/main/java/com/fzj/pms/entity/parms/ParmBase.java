package com.fzj.pms.entity.parms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ParmBase {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private int deleteFlag;

    private int pageSize;

    private int currentPage;

    private Long userId;

}
