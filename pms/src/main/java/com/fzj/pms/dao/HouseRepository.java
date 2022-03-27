package com.fzj.pms.dao;

import com.fzj.pms.entity.pms.House;
import com.fzj.pms.entity.pms.Repairs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House,Long> {

    Page<House> findAll(Specification<House> specification, Pageable pageable);

}
