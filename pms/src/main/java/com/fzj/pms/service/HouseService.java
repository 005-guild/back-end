package com.fzj.pms.service;

import com.fzj.pms.entity.pms.House;
import org.springframework.data.domain.Page;

public interface HouseService {

    void update(House house);

    void delete(Long id);

    House create(House house);

    Page<House> search(House house,int pageSize,int currentPage);
}
