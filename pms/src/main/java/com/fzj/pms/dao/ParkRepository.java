package com.fzj.pms.dao;

import com.fzj.pms.entity.pms.Park;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkRepository extends JpaRepository<Park, Long> {

    Optional<Park> findParkByUserId(Long id);

    Page<Park> findAll(Specification<Park> specification, Pageable pageable);
}
