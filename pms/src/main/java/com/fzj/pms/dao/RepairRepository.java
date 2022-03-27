package com.fzj.pms.dao;

import com.fzj.pms.entity.pms.Repairs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairRepository extends JpaRepository<Repairs,Long> {

    Optional<Repairs> findRepairsByUserId(Long id);

    Page<Repairs> findAll(Specification<Repairs> specification, Pageable pageable);
}
