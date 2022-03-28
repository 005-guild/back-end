package com.fzj.pms.dao;

import com.fzj.pms.entity.pms.PayUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayUserRepository extends JpaRepository<PayUser,Long> {

    void deleteAllByPayId(Long pid);

    Page<PayUser> findAll(Specification<PayUser> specification, Pageable pageable);
}
