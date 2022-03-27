package com.fzj.pms.entity.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class Base implements Serializable {

    @Id
    @NotNull(groups = {Update.class},message = "ID 不能为空")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
    @CreationTimestamp
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

//    @JsonIgnore
    @UpdateTimestamp
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date updateTime;

    @JsonIgnore
    @NotNull
    private int deleteFlag;

    @Transient
    private int pageSize;

    @Transient
    private int currentPage;

    public interface Update{}
}
