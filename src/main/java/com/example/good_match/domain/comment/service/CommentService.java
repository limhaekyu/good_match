package com.example.good_match.domain.comment.service;

import com.example.good_match.domain.comment.dto.request.AddCommentRequestDto;
import com.example.good_match.domain.comment.dto.request.UpdateCommentRequestDto;
import com.example.good_match.domain.comment.model.Comment;
import com.example.good_match.global.response.ApiResponseDto;
import org.springframework.security.core.userdetails.User;

public interface CommentService {
    public ApiResponseDto addComment(AddCommentRequestDto addCommentRequestDto, User user);

    public ApiResponseDto updateComment(User user, Long commentId, UpdateCommentRequestDto updateCommentRequestDto);

    public ApiResponseDto cancelComment(User user, Long commentId);

    public Comment findCommentById(Long commentId);

}
