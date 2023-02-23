package com.example.good_match.domain.category.service;

import com.example.good_match.domain.category.dto.request.InsertCategoryRequestDto;
import com.example.good_match.domain.category.dto.response.BoardsByCategoryResponseDto;
import com.example.good_match.domain.category.dto.response.CategoryResponseDto;
import com.example.good_match.domain.category.dto.response.SubCategoryResponseDto;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.util.StatesEnum;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface CategoryService {

    ApiResponseDto insertCategory(User user, InsertCategoryRequestDto insertCategoryRequest);

    ApiResponseDto<List<CategoryResponseDto>> selectCategoryList();

    ApiResponseDto<List<SubCategoryResponseDto>> selectSubCategoryList(Long categoryId);

    Category findCategoryById(Long categoryId);

    ApiResponseDto<BoardsByCategoryResponseDto> selectBoardByCategory(Long categoryId, StatesEnum states);


}
