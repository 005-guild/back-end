package com.fzj.pms.service.impl;

import com.fzj.pms.dao.PayRepository;
import com.fzj.pms.dao.PayUserRepository;
import com.fzj.pms.entity.pms.Pay;
import com.fzj.pms.entity.pms.PayUser;
import com.fzj.pms.service.PayUserService;
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
public class PayUserServiceImpl implements PayUserService {

    @Autowired
    private PayUserRepository payUserRepository;

    @Autowired
    private PayRepository payRepository;

    @Override
    public PayUser create(PayUser payUser) {
        return payUserRepository.save(payUser);
    }

    @Override
    public void update(PayUser payUser) {
        payUserRepository.findById(payUser.getId()).ifPresent(payUser1 -> {
//            payUser1.setPay(payUser.getPay());
//            payUser1.setUser(payUser.getUser());
            payUser1.setPayDate(payUser.getPayDate());
            payUser1.setPayStatus(payUser.getPayStatus());
            payUser1.setDeadLine(payUser.getDeadLine());
//            payUser1.setPayName(payUser1.getPayName());
            payUserRepository.save(payUser1);
        });
    }

    @Override
    public void delete(Long id) {
        payUserRepository.deleteById(id);
    }

    @Override
    public void deleteAllByPid(Long pid) {
        payUserRepository.deleteAllByPayId(pid);
    }

    @Override
    public Page<PayUser> search(PayUser payUser, int pageSize, int currentPage) {
        if(pageSize == 0) {
            pageSize = 10 ;
        }
        if(currentPage < 1){
            currentPage = 1 ;
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(currentPage-1,pageSize,sort);
        Specification<PayUser> specification = new Specification<PayUser>() {
            @Override
            public Predicate toPredicate(Root<PayUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(StringUtils.isNotBlank(payUser.getPayName())){
                    predicates.add(criteriaBuilder.like(root.get("payName"),"%"+payUser.getPayName()+"%"));
                }

                if(!Objects.isNull(payUser.getPayStatus())){
                    predicates.add(criteriaBuilder.equal(root.get("payStatus"), payUser.getPayStatus()));
                }

                if(!Objects.isNull(payUser.getUser())){
                    if(!Objects.isNull(payUser.getUser().getId())){
                        predicates.add(criteriaBuilder.equal(root.get("user"),payUser.getUser()));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };

        return payUserRepository.findAll(specification,pageable);
    }
}
