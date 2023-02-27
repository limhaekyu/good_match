package com.example.good_match.domain.category.controller;

import com.example.good_match.domain.category.dto.request.InsertCategoryRequestDto;
import com.example.good_match.domain.category.dto.response.PostListByCategoryResponseDto;
import com.example.good_match.domain.category.dto.response.CategoryResponseDto;
import com.example.good_match.domain.category.dto.response.SubCategoryResponseDto;
import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.global.annotation.CurrentMemberId;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.util.StatesEnum;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @ApiOperation(value = "[카테고리] 카테고리 등록")
    @PostMapping("")
    public ApiResponseDto insertCategory(@CurrentMemberId Long memberId, @RequestBody InsertCategoryRequestDto insertCategoryRequest) {
        return categoryService.insertCategory(memberId, insertCategoryRequest);
    }

    @ApiOperation(value = "[카테고리] 전체 카테고리 리스트")
    @GetMapping("")
    public ApiResponseDto<List<CategoryResponseDto>> selectCategoryList(){
        return categoryService.selectCategoryList();
    }

    @ApiOperation(value = "[카테고리] 서브카테고리 조회")
    @GetMapping("/{category-id}/subs")
    public ApiResponseDto<List<SubCategoryResponseDto>> selectCategory(@PathVariable("category-id") Long categoryId){
        return categoryService.selectSubCategoryList(categoryId);
    }

    @ApiOperation(value = "[카테고리] 카테고리별 게시글 조회")
    @GetMapping("/{category-id}/posts")
    public ApiResponseDto<PostListByCategoryResponseDto> selectPostByCategory(@PathVariable("category-id") Long categoryId, StatesEnum states) {
        return categoryService.selectPostByCategory(categoryId, states);
    }

}
