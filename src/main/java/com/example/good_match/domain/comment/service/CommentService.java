package com.example.good_match.domain.comment.service;

import com.example.good_match.domain.comment.dto.request.InsertCommentRequestDto;
import com.example.good_match.domain.comment.dto.request.UpdateCommentRequestDto;
import com.example.good_match.domain.comment.model.Comment;
import com.example.good_match.global.response.ApiResponseDto;
import org.springframework.security.core.userdetails.User;

public interface CommentService {
    public ApiResponseDto insertComment(InsertCommentRequestDto insertCommentRequestDto, Long memberId);

    public ApiResponseDto updateComment(Long memberId, Long commentId, UpdateCommentRequestDto updateCommentRequestDto);

    public ApiResponseDto cancelComment(Long memberId, Long commentId);

    public Comment findCommentById(Long commentId);

}
