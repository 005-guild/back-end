package com.fzj.pms.service;

import com.fzj.pms.entity.pms.Park;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParkService {

    void update(Park park);

    void delete(Long id);

    Park create(Park park);

    Optional<Park> getParkByUserId(Long userId);

    Page<Park> search(Park park,int pageSize,int currentPage);
}
