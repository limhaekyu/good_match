package com.example.good_match.domain.board.repository;

import com.example.good_match.domain.board.domain.Board;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.global.util.StatesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByStatesAndCategory(StatesEnum states, Category category);
}
