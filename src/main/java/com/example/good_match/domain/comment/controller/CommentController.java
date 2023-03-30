package com.example.good_match.domain.comment.controller;

import com.example.good_match.domain.comment.dto.request.InsertCommentRequestDto;
import com.example.good_match.domain.comment.dto.request.UpdateCommentRequestDto;
import com.example.good_match.domain.comment.service.CommentService;
import com.example.good_match.global.annotation.CurrentMemberId;
import com.example.good_match.global.annotation.LoginRequired;
import com.example.good_match.global.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @ApiOperation(value = "[댓글] 댓글 등록")
    @LoginRequired
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void insertComment(@CurrentMemberId Long memberId, @RequestBody InsertCommentRequestDto insertCommentRequestDto) {
        commentService.insertComment(insertCommentRequestDto, memberId);
    }

    @ApiOperation(value = "[댓글] 댓글 수정")
    @LoginRequired
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void updateComment(@CurrentMemberId Long memberId, @PathVariable("id") Long commentId, @RequestBody UpdateCommentRequestDto updateCommentRequestDto) {
        commentService.updateComment(memberId, commentId, updateCommentRequestDto);
    }

    @ApiOperation(value = "[댓글] 댓글 삭제")
    @LoginRequired
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void cancelComment(@CurrentMemberId Long memberId, @PathVariable("id") Long commentId) {
        commentService.cancelComment(memberId, commentId);
    }
}


