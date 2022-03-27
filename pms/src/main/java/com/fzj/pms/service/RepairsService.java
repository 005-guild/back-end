package com.fzj.pms.service;

import com.fzj.pms.entity.pms.Repairs;
import org.springframework.data.domain.Page;

public interface RepairsService {

    void update(Repairs repairs);

    void delete(Long id);

    Repairs create(Repairs repairs);

    Page<Repairs> search(Repairs repairs,int pageSize,int currentPage);
}
