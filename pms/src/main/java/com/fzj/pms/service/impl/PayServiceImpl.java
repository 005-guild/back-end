package com.fzj.pms.service.impl;

import com.fzj.pms.dao.PayRepository;
import com.fzj.pms.dao.PayUserRepository;
import com.fzj.pms.entity.pms.Pay;
import com.fzj.pms.service.PayService;
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
public class PayServiceImpl implements PayService {

    @Autowired
    private PayRepository payRepository;

    @Autowired
    private PayUserRepository payUserRepository;

    @Override
    public Pay create(Pay pay) {
        return payRepository.save(pay);
    }

    @Override
    public void update(Pay pay) {
        payRepository.findById(pay.getId()).ifPresent(pays->{
            pays.setPosition(pay.getPosition());
            pays.setBuilding(pay.getBuilding());
            pays.setUnit(pay.getUnit());
            pays.setPayName(pay.getPayName());
            pays.setMoney(pay.getMoney());
            pays.setDeadLine(pay.getDeadLine());
            pays.setUseStatus(pay.getUseStatus());
            pays.setNum(pay.getNum());
            payRepository.save(pays);
        });
    }

    @Override
    public void delete(Long id) {
        if(payRepository.findById(id).isPresent()){
            payUserRepository.deleteAllByPayId(id);
            payRepository.deleteById(id);
        }
    }

    @Override
    public Page<Pay> search(Pay pay, int pageSize, int currentPage) {
        if(pageSize == 0) {
            pageSize = 10 ;
        }
        if(currentPage < 1){
            currentPage = 1 ;
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(currentPage-1,pageSize,sort);
        Specification<Pay> specification = new Specification<Pay>() {
            @Override
            public Predicate toPredicate(Root<Pay> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates =new ArrayList<>();
                if(StringUtils.isNotBlank(pay.getPayName())){
                    predicates.add(criteriaBuilder.like(root.get("payName"),"%"+pay.getPayName()+"%"));
                }

                if(StringUtils.isNotBlank(pay.getBuilding())){
                    predicates.add(criteriaBuilder.like(root.get("building"),"%"+pay.getBuilding()+"%"));
                }

                if(StringUtils.isNotBlank(pay.getUnit())){
                    predicates.add(criteriaBuilder.like(root.get("unit"),"%"+pay.getUnit()+"%"));
                }

                if(StringUtils.isNotBlank(pay.getPosition())){
                    predicates.add(criteriaBuilder.like(root.get("position"),"%"+pay.getPosition()+"%"));
                }

                if(!Objects.isNull(pay.getUseStatus())){
                    predicates.add(criteriaBuilder.equal(root.get("useStatus"),pay.getUseStatus()));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return payRepository.findAll(specification,pageable);
    }
}
