package com.fzj.pms.service.impl;

import com.fzj.pms.dao.RepairRepository;
import com.fzj.pms.entity.pms.Repairs;
import com.fzj.pms.service.RepairsService;
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
public class RepairsServiceImpl implements RepairsService {

    @Autowired
    private RepairRepository repairRepository;

    @Override
    public void update(Repairs repairs) {
        repairRepository.findById(repairs.getId()).ifPresent(repair -> {
//            repair.setRepairsDate(repairs.getRepairsDate());
//            repair.setRepairsType(repairs.getRepairsType());
            repair.setRepairsStatus(repairs.getRepairsStatus());
            repair.setLinkAddress(repairs.getLinkAddress());
//            repair.setRepairsPrice(repairs.getRepairsPrice());
//            repair.setRepairsBillNo(repairs.getRepairsBillNo());
            repair.setFinishDate(repairs.getFinishDate());
            repair.setLinkman(repairs.getLinkman());
            repair.setLinkPhone(repairs.getLinkPhone());
            repair.setRemark(repairs.getRemark());
            repairRepository.save(repair);
        });
    }

    @Override
    public void delete(Long id) {
        repairRepository.deleteById(id);
    }

    @Override
    public Repairs create(Repairs repairs) {
        return repairRepository.save(repairs);
    }

    @Override
    public Page<Repairs> search(Repairs repairs, int pageSize, int currentPage) {
        if(pageSize == 0) {
            pageSize = 10 ;
        }
        if(currentPage < 1){
            currentPage = 1 ;
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(currentPage-1,pageSize,sort);
        Specification<Repairs> specification = new Specification<Repairs>() {
            @Override
            public Predicate toPredicate(Root<Repairs> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(StringUtils.isNotBlank(repairs.getRemark())){
                    predicates.add(criteriaBuilder.like(root.get("remark"),"%" + repairs.getRemark()+ "%"));
                }

                if(!Objects.isNull(repairs.getUser())){
                    if(!Objects.isNull(repairs.getUser().getId())){
                        predicates.add(criteriaBuilder.equal(root.get("user"),repairs.getUser()));
                    }
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return repairRepository.findAll(specification,pageable);
    }
}
