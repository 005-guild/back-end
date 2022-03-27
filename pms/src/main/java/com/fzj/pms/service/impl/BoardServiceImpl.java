package com.fzj.pms.service.impl;

import com.fzj.pms.dao.BoardRepository;
import com.fzj.pms.dao.UserRepository;
import com.fzj.pms.entity.pms.Board;
import com.fzj.pms.service.BoardService;
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
import java.util.Optional;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    public void update(Board board) {
        boardRepository.findById(board.getId()).ifPresent(board1 -> {
            board1.setBoardContent(board.getBoardContent());
            board1.setLinkName(board.getLinkName());
            board1.setLinkPhone(board.getLinkPhone());
        });
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public Board create(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public Optional<Board> getBoardByUserId(Long userId) {
        return boardRepository.findBoardByUserId(userId);
    }

    @Override
    public Page<Board> search(Board board, int pageSize, int currentPage) {
        if(pageSize == 0) {
            pageSize = 10 ;
        }
        if(currentPage < 1){
            currentPage = 1 ;
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(currentPage-1,pageSize,sort);
        Specification<Board> specification = new Specification<Board>() {
            @Override
            public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(StringUtils.isNotBlank(board.getBoardContent())){
                    predicates.add(criteriaBuilder.like(root.get("boardContent"),"%"+board.getBoardContent()+"%"));
                }

                if(StringUtils.isNotBlank(board.getTitle())){
                    predicates.add(criteriaBuilder.like(root.get("title"),"%"+board.getTitle()+"%"));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return boardRepository.findAll(specification,pageable);
    }
}
