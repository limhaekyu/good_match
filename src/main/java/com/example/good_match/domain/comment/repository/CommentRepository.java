package com.example.good_match.domain.comment.repository;

import com.example.good_match.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
