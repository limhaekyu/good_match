package com.example.good_match.domain.post.service;

import com.example.good_match.domain.post.dto.request.AddPostRequestDto;
import com.example.good_match.domain.post.dto.request.UpdatePostRequestDto;
import com.example.good_match.global.response.ApiResponseDto;
import org.springframework.security.core.userdetails.User;
public interface PostService {

    public ApiResponseDto insertPost(AddPostRequestDto addPostRequestDto, User user);

    public ApiResponseDto selectPostDetail(Long id);

    public ApiResponseDto deletePost(Long id, User user);

    public ApiResponseDto updatePost(Long id, UpdatePostRequestDto updatePostRequestDto, User user);
}
