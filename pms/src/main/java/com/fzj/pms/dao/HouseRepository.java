package com.fzj.pms.dao;

import com.fzj.pms.entity.pms.House;
import com.fzj.pms.entity.pms.Repairs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House,Long> {

    Page<House> findAll(Specification<House> specification, Pageable pageable);

    List<House> findByBuilding(String building);

    List<House> findByBuildingAndUnit(String building,String unit);

    Optional<House> findByBuildingAndUnitAndPosition(String building, String unit, String position);
}
