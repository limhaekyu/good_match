package com.example.good_match.domain.comment.controller;

import com.example.good_match.domain.comment.dto.request.AddCommentRequestDto;
import com.example.good_match.domain.comment.service.CommentService;
import com.example.good_match.global.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ApiResponseDto addComment(@AuthenticationPrincipal User user, @RequestBody AddCommentRequestDto addCommentRequestDto){
        return commentService.addComment(addCommentRequestDto, user);
    }
}
