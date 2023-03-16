package com.example.good_match.domain.post.repository;

import com.example.good_match.domain.main.dto.response.PostResponseDto;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.global.util.StatesEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByStatesAndCategory(StatesEnum states, Category category);

    @Query("select p from Post p order by p.updatedAt desc")
    List<Post> findCurrentPosts(Pageable pageable);
}
