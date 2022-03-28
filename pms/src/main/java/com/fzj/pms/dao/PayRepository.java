package com.fzj.pms.dao;

import com.fzj.pms.entity.pms.Pay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay,Long> {

    Page<Pay> findAll(Specification<Pay> specification, Pageable pageable);

}
