package com.example.good_match.domain.category.service;

import com.example.good_match.domain.category.dto.request.InsertCategoryRequestDto;
import com.example.good_match.domain.category.dto.response.PostListByCategoryResponseDto;
import com.example.good_match.domain.category.dto.response.CategoryResponseDto;
import com.example.good_match.domain.category.dto.response.SubCategoryResponseDto;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.util.StatesEnum;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface CategoryService {

    void insertCategory(InsertCategoryRequestDto insertCategoryRequest);

    List<CategoryResponseDto> selectCategoryList();

    List<SubCategoryResponseDto> selectSubCategoryList(Long categoryId);

    Category findCategoryById(Long categoryId);

    SubCategory findSubCategoryById(Long subCategoryId);

    PostListByCategoryResponseDto selectPostByCategory(Long categoryId, StatesEnum states);


}
