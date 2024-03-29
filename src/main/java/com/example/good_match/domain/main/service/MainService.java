package com.example.good_match.domain.main.service;

import com.example.good_match.domain.main.dto.response.MainPostsByCategoryResponseDto;
import com.example.good_match.domain.main.dto.response.MainResponseDto;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.util.StatesEnum;

public interface MainService {

    MainResponseDto selectMainInfo();

    MainPostsByCategoryResponseDto selectMainPostsBySubCategory(Long subCategoryId, Integer pageNumber);
}
