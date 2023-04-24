package com.example.good_match.domain.post.controller;

import com.example.good_match.domain.post.dto.request.AddPostRequestDto;
import com.example.good_match.domain.post.dto.request.UpdatePostRequestDto;
import com.example.good_match.domain.post.dto.response.SelectPostDetailResponseDto;
import com.example.good_match.domain.post.service.PostService;
import com.example.good_match.global.annotation.CurrentMemberId;
import com.example.good_match.global.annotation.LoginRequired;
import com.example.good_match.global.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "[게시글] 게시글 등록")
    @LoginRequired
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void insertPost(@CurrentMemberId Long memberId, @RequestBody AddPostRequestDto addPostRequestDto) {
        postService.insertPost(addPostRequestDto, memberId);
    }

    @ApiOperation(value = "[게시글] 게시글 상세조회")
    @GetMapping("/{id}")
    public ResponseEntity<SelectPostDetailResponseDto> selectPostDetail(@PathVariable Long id){
        return ResponseEntity.ok().body(postService.selectPostDetail(id));
    }

    @ApiOperation(value = "[게시글] 게시글 삭제")
    @LoginRequired
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, @CurrentMemberId Long memberId) {
        postService.deletePost(id, memberId);
    }

    @ApiOperation(value = "[게시글] 게시글 수정")
    @LoginRequired
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody UpdatePostRequestDto updatePostRequestDto, @CurrentMemberId Long memberId) {
        postService.updatePost(id, updatePostRequestDto, memberId);
    }

}
