package com.example.good_match.domain.category.service;

import com.example.good_match.domain.category.dto.response.CategoryResponseDto;
import com.example.good_match.domain.category.dto.response.SubCategoryResponseDto;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.repository.SubCategoryRepository;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    // 전체 카테고리 불러오기
    public ApiResponseDto getCategoryList() {
        try {
            List<CategoryResponseDto> categoryResponseList = categoryRepository.findAll().stream().map(this::response).toList();

            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "카테고리 조회 성공", categoryResponseList);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "카테고리 조회를 실패했습니다 " + e.getMessage());
        }
    }

    // 카테고리 별 서브 카테고리 호출
    public ApiResponseDto getSubCategoryList(Long categoryId) {
        try {

            List<SubCategoryResponseDto> subCategoryResponselist = subCategoryRepository.findAllByCategoryId(categoryId).stream().map(this::subResponse).toList();
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "서브 카테고리 조회 성공", subCategoryResponselist);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "서브 카테고리 조회 실패 "+e.getMessage());
        }
    }

    // 카테고리 별 서브 카테고리 매핑
    private CategoryResponseDto response(Category category) {
        List<SubCategoryResponseDto> subCategoryResponseList = new ArrayList<>();

        if(category.getSubCategories() != null) {
            for(SubCategory subCategory : category.getSubCategories()){
                subCategoryResponseList.add(
                        SubCategoryResponseDto.builder()
                            .subCategoryId(subCategory.getId())
                            .subCategoryTitle(subCategory.getSubCategoryTitle())
                            .build());
            }
        }

        return CategoryResponseDto.builder()
                .categoryId(category.getId())
                .title(category.getTitle())
                .subCategories(subCategoryResponseList)
                .build();
    }

    private SubCategoryResponseDto subResponse(SubCategory subCategory) {
        return SubCategoryResponseDto.builder()
                .subCategoryId(subCategory.getId())
                .subCategoryTitle(subCategory.getSubCategoryTitle())
                .build();
    }
}
