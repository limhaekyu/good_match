package com.example.good_match.domain.post.service;

import com.example.good_match.domain.post.dto.response.SearchPostResponseDto;
import com.example.good_match.global.util.StatesEnum;

import java.util.List;

public interface SearchPostService {
    public List<SearchPostResponseDto> searchPosts(StatesEnum city, Long categoryId, String keyword);
}
