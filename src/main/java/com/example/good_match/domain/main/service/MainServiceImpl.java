package com.example.good_match.domain.main.service;

import com.example.good_match.domain.category.dto.response.CategoryResponseDto;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.main.dto.response.MainCategoryDto;
import com.example.good_match.domain.main.dto.response.MainResponseDto;
import com.example.good_match.domain.main.dto.response.MainSubCategoryDto;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final CategoryRepository categoryRepository;

    @Override
    public ApiResponseDto<MainResponseDto> showMainInfo() {
        try {
            MainResponseDto mainResponse = MainResponseDto.builder()
                    .mainCategoryList(selectCategoryList())
                    .mainBoardList(null)
                    .build();
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), " 메인 조회 성공! " ,mainResponse);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.INTERNAL_SERVER_ERROR.getValue(), e.getMessage());
        }
    }

    private List<MainCategoryDto> selectCategoryList(){
        List<Category> categoryList = categoryRepository.findAll();
        List<MainCategoryDto> mainCategoryList = new ArrayList<>();
        for (Category category : categoryList) {
            mainCategoryList.add(MainCategoryDto.builder()
                            .id(category.getId())
                            .title(category.getTitle())
                            .subCategoryList(makeDtoSubCategory(category.getSubCategories()))
                    .build());
        }
        return mainCategoryList;
    }

    private List<MainSubCategoryDto> makeDtoSubCategory(List<SubCategory> subCategoryList) {
        List<MainSubCategoryDto> mainSubCategoryList = new ArrayList<>();
        for (SubCategory subCategory : subCategoryList) {
            mainSubCategoryList.add(MainSubCategoryDto.builder()
                            .subCategoryId(subCategory.getId())
                            .subCategoryTitle(subCategory.getSubCategoryTitle())
                    .build());
        }
        return mainSubCategoryList;
    }
}
