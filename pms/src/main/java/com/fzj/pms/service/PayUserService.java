package com.fzj.pms.service;

import com.fzj.pms.entity.pms.PayUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PayUserService {

    PayUser create(PayUser payUser);

    void update(PayUser payUser);

    void delete(Long id);

    void deleteAllByPid(Long pid);

    Page<PayUser> search(PayUser payUser,int pageSize,int currentPage);
}
