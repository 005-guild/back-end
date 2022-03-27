package com.fzj.pms.service;

import com.fzj.pms.entity.pms.Board;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BoardService {

    void update(Board board);

    void delete(Long id);

    Board create(Board board);

    Optional<Board> getBoardByUserId(Long userId);

    Optional<Board> findById(Long id);

    Page<Board> search(Board board,int pageSize,int currentPage);
}
