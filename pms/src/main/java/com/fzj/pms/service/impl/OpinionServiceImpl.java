package com.fzj.pms.service.impl;

import com.fzj.pms.dao.OpinionRepository;
import com.fzj.pms.entity.pms.Opinion;
import com.fzj.pms.service.OpinionService;
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

@Service("opinionService")
public class OpinionServiceImpl implements OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;

    @Override
    public void update(Opinion opinion) {
        opinionRepository.findById(opinion.getId()).ifPresent(opinions -> {
            opinions.setOpinionTitle(opinion.getOpinionTitle());
            opinions.setOpinionContent(opinion.getOpinionContent());
            opinions.setReplyStatus(opinion.getReplyStatus());
            opinions.setReplyDate(opinion.getReplyDate());
            opinions.setReplyId(opinion.getReplyId());
            opinions.setReplyContent(opinion.getReplyContent());
            opinionRepository.save(opinions);
        });
    }

    @Override
    public void delete(long id) {
        opinionRepository.deleteById(id);
    }

    @Override
    public Opinion create(Opinion opinion) {
        //System.out.println(opinion.getReplyStatus());
        return opinionRepository.save(opinion);
    }

    @Override
    public Optional<Opinion> getOpinionByUserId(Long userId) {
        return opinionRepository.findOpinionByUserId(userId);
    }

    @Override
    public List<Opinion> findAll() {
        return null;
    }

    @Override
    public Page<Opinion> search(Opinion opinion, int pageSize, int currentPage) {
        if(pageSize == 0) {
            pageSize = 10 ;
        }
        if(currentPage < 1){
            currentPage = 1 ;
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(currentPage-1,pageSize,sort);
        Specification<Opinion> specification = new Specification<Opinion>() {
            @Override
            public Predicate toPredicate(Root<Opinion> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(opinion.getOpinionTitle())){
                    predicates.add(criteriaBuilder.like(root.get("opinionTitle"), "%" + opinion.getOpinionTitle() + "%"));
                }

                if (StringUtils.isNotBlank(opinion.getOpinionContent())){
                    predicates.add(criteriaBuilder.like(root.get("opinionContent"), opinion.getOpinionContent() + "%"));
                }

                if(!Objects.isNull(opinion.getUser())){
                    if (!Objects.isNull(opinion.getUser().getId())){
                        predicates.add(criteriaBuilder.equal(root.get("user"),opinion.getUser()));
                    }
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return opinionRepository.findAll(specification,pageable);
    }
}
