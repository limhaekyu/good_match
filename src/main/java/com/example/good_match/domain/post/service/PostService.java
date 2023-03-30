package com.example.good_match.domain.post.service;

import com.example.good_match.domain.post.dto.request.AddPostRequestDto;
import com.example.good_match.domain.post.dto.request.UpdatePostRequestDto;
import com.example.good_match.domain.post.dto.response.SelectPostDetailResponseDto;
import com.example.good_match.global.response.ApiResponseDto;
import org.springframework.security.core.userdetails.User;
public interface PostService {

    public void insertPost(AddPostRequestDto addPostRequestDto, Long memberId);

    public SelectPostDetailResponseDto selectPostDetail(Long id);

    public void deletePost(Long id, Long memberId);

    public void updatePost(Long id, UpdatePostRequestDto updatePostRequestDto, Long memberId);
}
