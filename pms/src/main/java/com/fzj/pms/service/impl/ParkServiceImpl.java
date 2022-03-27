package com.fzj.pms.service.impl;

import com.fzj.pms.dao.ParkRepository;
import com.fzj.pms.entity.pms.Opinion;
import com.fzj.pms.entity.pms.Park;
import com.fzj.pms.service.ParkService;
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
import java.util.Optional;

@Service
public class ParkServiceImpl implements ParkService {

    @Autowired
    private ParkRepository parkRepository;

    @Override
    public void update(Park park) {
        parkRepository.findById(park.getId()).ifPresent(parks -> {
            parks.setParkType(park.getParkType());
            parks.setPosition(park.getPosition());
            parks.setUser(park.getUser());
            parks.setExpireDate(park.getExpireDate());
            parks.setUseStatus(park.getUseStatus());
            parkRepository.save(parks);
        });
    }

    @Override
    public void delete(Long id) {
        parkRepository.deleteById(id);
    }

    @Override
    public Park create(Park park) {
        return parkRepository.save(park);
    }

    @Override
    public Optional<Park> getParkByUserId(Long userId) {
        return parkRepository.findParkByUserId(userId);
    }

    @Override
    public Page<Park> search(Park park, int pageSize, int currentPage) {
        if(pageSize == 0) {
            pageSize = 10 ;
        }
        if(currentPage < 1){
            currentPage = 1 ;
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(currentPage-1,pageSize,sort);
        Specification<Park> specification = new Specification<Park>() {
            @Override
            public Predicate toPredicate(Root<Park> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(StringUtils.isNotBlank(park.getPosition())){
                    predicates.add(criteriaBuilder.like(root.get("position"),"%"+park.getPosition()+"%"));
                }

                if(!Objects.isNull(park.getParkType())){
                    predicates.add(criteriaBuilder.equal(root.get("parkType"),park.getParkType()));
                }

                if(!Objects.isNull(park.getUseStatus())){
                    predicates.add(criteriaBuilder.equal(root.get("useStatus"),park.getUseStatus()));
                }

                if(!Objects.isNull(park.getUser())){
                    if(!Objects.isNull(park.getUser().getId())){
                        predicates.add(criteriaBuilder.equal(root.get("user"),park.getUser()));
                    }
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return parkRepository.findAll(specification,pageable);
    }
}
