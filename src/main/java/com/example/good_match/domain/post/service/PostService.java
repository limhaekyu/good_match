package com.example.good_match.domain.post.service;

import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.dto.request.AddPostRequestDto;
import com.example.good_match.domain.post.dto.request.UpdatePostRequestDto;
import com.example.good_match.domain.post.dto.response.SelectPostDetailResponseDto;
import com.example.good_match.global.response.ApiResponseDto;
import org.springframework.security.core.userdetails.User;
public interface PostService {

    void insertPost(AddPostRequestDto addPostRequestDto, Long memberId);

    SelectPostDetailResponseDto selectPostDetail(Long id);

    void deletePost(Long id, Long memberId);

    void updatePost(Long id, UpdatePostRequestDto updatePostRequestDto, Long memberId);

    Post findPostById(Long id);
}
