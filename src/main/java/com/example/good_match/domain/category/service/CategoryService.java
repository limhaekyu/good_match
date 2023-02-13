package com.example.good_match.domain.category.service;

import com.example.good_match.global.response.ApiResponseDto;

public interface CategoryService {
     public ApiResponseDto getCategoryList();

    public ApiResponseDto getSubCategoryList(Long categoryId);

}
