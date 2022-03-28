package com.fzj.pms.service;

import com.fzj.pms.entity.pms.House;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface HouseService {

    void update(House house);

    void delete(Long id);

    House create(House house);

    Page<House> search(House house,int pageSize,int currentPage);

    List<House> findByBuilding(String building);

    List<House> findByBuildingAndUnit(String building,String unit);

    Optional<House> findByBuildingAndUnitAndPosition(String building, String unit, String position);
}
