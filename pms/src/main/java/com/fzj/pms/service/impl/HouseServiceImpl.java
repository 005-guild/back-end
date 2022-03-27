package com.fzj.pms.service.impl;

import com.fzj.pms.dao.HouseRepository;
import com.fzj.pms.entity.pms.House;
import com.fzj.pms.service.HouseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Override
    public void update(House house) {
        houseRepository.findById(house.getId()).ifPresent(house1 -> {
            house1.setBuilding(house.getBuilding());
            house1.setPosition(house.getPosition());
            house1.setUnit(house.getUnit());
            house1.setUsername(house.getUsername());
            house1.setUseStatus(house.getUseStatus());
            house1.setUser(house.getUser());
            houseRepository.save(house1);
        });
    }

    @Override
    public void delete(Long id) {
        houseRepository.deleteById(id);
    }

    @Override
    public House create(House house) {
        return houseRepository.save(house);
    }

    @Override
    public Page<House> search(House house, int pageSize, int currentPage) {
        if(pageSize == 0) {
            pageSize = 10 ;
        }
        if(currentPage < 1){
            currentPage = 1 ;
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(currentPage-1,pageSize,sort);
        Specification<House> specification = new Specification<House>() {
            @Override
            public Predicate toPredicate(Root<House> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(StringUtils.isNotBlank(house.getBuilding())){
                    predicates.add(criteriaBuilder.like(root.get("building"),house.getBuilding()+"%"));
                }

                if(StringUtils.isNotBlank(house.getUnit())){
                    predicates.add(criteriaBuilder.like(root.get("unit"),house.getUnit()+"%"));
                }

                if(StringUtils.isNotBlank(house.getPosition())){
                    predicates.add(criteriaBuilder.like(root.get("position"),house.getPosition()+"%"));
                }

                if(StringUtils.isNotBlank(house.getUsername())){
                    predicates.add(criteriaBuilder.like(root.get("userName"),"%"+house.getUsername()+"%"));
                }

                if(!Objects.isNull(house.getUseStatus())){
                    predicates.add(criteriaBuilder.equal(root.get("useStatus"),house.getUseStatus()));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return houseRepository.findAll(specification,pageable);
    }
}
