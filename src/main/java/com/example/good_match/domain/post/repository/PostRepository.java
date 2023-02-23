package com.example.good_match.domain.post.repository;

import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.global.util.StatesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByStatesAndCategory(StatesEnum states, Category category);
}
