package com.example.good_match.domain.category.controller;

import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.global.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @ApiOperation(value = "[카테고리] 전체 카테고리 리스트")
    @GetMapping("")
    public ApiResponseDto getCategoryList(){
        return categoryService.getCategoryList();
    }

    @ApiOperation(value = "[카테고리] 서브카테고리 조회")
    @GetMapping("/{id}")
    public ApiResponseDto getCateogry(@PathVariable("id") Long categoryId){
        return categoryService.getSubCategoryList(categoryId);
    }

}
