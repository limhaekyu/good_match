package com.example.good_match.domain.post.controller;

import com.example.good_match.domain.post.dto.request.AddPostRequestDto;
import com.example.good_match.domain.post.dto.request.UpdatePostRequestDto;
import com.example.good_match.domain.post.service.PostService;
import com.example.good_match.global.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "[게시글] 게시글 등록")
    @PostMapping("")
    public ApiResponseDto insertPost(@AuthenticationPrincipal User user, @RequestBody AddPostRequestDto addPostRequestDto) {
        return postService.insertPost(addPostRequestDto, user);
    }

    @ApiOperation(value = "[게시글] 게시글 상세조회")
    @GetMapping("/{id}")
    public ApiResponseDto selectPostDetail(@PathVariable Long id){
        return postService.selectPostDetail(id);
    }

    @ApiOperation(value = "[게시글] 게시글 삭제")
    @DeleteMapping("/{id}")
    public ApiResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return postService.deletePost(id, user);
    }

    @ApiOperation(value = "[게시글] 게시글 수정")
    @PutMapping("/{id}")
    public ApiResponseDto updatePost(@PathVariable Long id, @RequestBody UpdatePostRequestDto updatePostRequestDto, @AuthenticationPrincipal User user) {
        return postService.updatePost(id, updatePostRequestDto, user);
    }

}
