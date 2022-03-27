package com.fzj.pms.entity.parms;

import lombok.Data;

@Data
public class UserParm {
    //页容量
    private int pageSize;
    //当前页
    private int currentPage;
    //姓名
    private String userName;
    //电话
    private String phone;
}
