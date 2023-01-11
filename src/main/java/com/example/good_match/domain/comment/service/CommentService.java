package com.example.good_match.domain.comment.service;

import com.example.good_match.domain.comment.dto.request.AddCommentRequestDto;
import com.example.good_match.domain.comment.model.Comment;
import com.example.good_match.domain.comment.repository.CommentRepository;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final MemberService memberService;

    public ApiResponseDto addComment(AddCommentRequestDto addCommentRequestDto, User user) {
        try {
            commentRepository.save(
                    Comment.builder()
                            .contents(addCommentRequestDto.getContents())
                            .build()
            );
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "댓글 작성 성공");
        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "댓글 작성 실패 "+e.getMessage());
        }
    }
}
