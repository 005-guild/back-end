package com.fzj.pms.service;

import com.fzj.pms.entity.pms.Pay;
import org.springframework.data.domain.Page;

public interface PayService {

    Pay create(Pay pay);

    void update(Pay pay);

    void delete(Long id);

    Page<Pay> search(Pay pay,int pageSize,int currentPage);
}
