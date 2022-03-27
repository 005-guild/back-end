package com.fzj.pms.dao;

import com.fzj.pms.entity.pms.Opinion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion,Long> {

    Optional<Opinion> findOpinionByUserId(Long id);

    Page<Opinion> findAll(Specification<Opinion> specification,Pageable pageable);
}
