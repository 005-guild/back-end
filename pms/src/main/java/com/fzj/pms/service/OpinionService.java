package com.fzj.pms.service;

import com.fzj.pms.entity.pms.Opinion;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OpinionService {

    void update(Opinion opinion);

    void delete(long id);

    Opinion create(Opinion opinion);

    Optional<Opinion> getOpinionByUserId(Long userId);

    List<Opinion> findAll();

    Page<Opinion> search(Opinion opinion,int pageSize,int currentPage);
}
