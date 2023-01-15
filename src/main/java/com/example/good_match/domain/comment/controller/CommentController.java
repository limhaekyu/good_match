package com.example.good_match.domain.comment.controller;

import com.example.good_match.domain.comment.dto.request.AddCommentRequestDto;
import com.example.good_match.domain.comment.dto.request.UpdateCommentRequestDto;
import com.example.good_match.domain.comment.service.CommentService;
import com.example.good_match.global.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ApiResponseDto addComment(@AuthenticationPrincipal User user, @RequestBody AddCommentRequestDto addCommentRequestDto) {
        return commentService.addComment(addCommentRequestDto, user);
    }

    @PutMapping("/{id}")
    public ApiResponseDto updateComment(@AuthenticationPrincipal User user, @PathVariable("id") Long commentId, @RequestBody UpdateCommentRequestDto updateCommentRequestDto) {
        return commentService.updateComment(user, commentId, updateCommentRequestDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponseDto cancelComment(@AuthenticationPrincipal User user, @PathVariable("id") Long commentId) {
        return commentService.cancelComment(user, commentId);
    }
}


