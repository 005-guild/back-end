package com.fzj.pms.dao;

import com.fzj.pms.entity.pms.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    Optional<Board> findBoardByUserId(Long id);

    Page<Board> findAll(Specification<Board> specification, Pageable pageable);
}
