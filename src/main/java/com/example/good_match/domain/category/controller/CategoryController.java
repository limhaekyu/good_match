package com.example.good_match.domain.category.controller;

import com.example.good_match.domain.category.dto.request.InsertCategoryRequestDto;
import com.example.good_match.domain.category.dto.response.PostListByCategoryResponseDto;
import com.example.good_match.domain.category.dto.response.CategoryResponseDto;
import com.example.good_match.domain.category.dto.response.SubCategoryResponseDto;
import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.global.annotation.Auth;
import com.example.good_match.global.annotation.CurrentMemberId;
import com.example.good_match.global.annotation.LoginRequired;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.util.StatesEnum;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Auth
    @LoginRequired
    @PostMapping("")
    public ResponseEntity<Void> insertCategory(@RequestBody InsertCategoryRequestDto insertCategoryRequest) {
        categoryService.insertCategory(insertCategoryRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "[카테고리] 전체 카테고리 리스트")
    @GetMapping("")
    public ResponseEntity<List<CategoryResponseDto>> selectCategoryList(){
        return ResponseEntity.ok(categoryService.selectCategoryList());
    }

    @ApiOperation(value = "[카테고리] 서브카테고리 조회")
    @GetMapping("/{category-id}/subs")
    public ResponseEntity<List<SubCategoryResponseDto>> selectCategory(@PathVariable("category-id") Long categoryId){
        return ResponseEntity.ok(categoryService.selectSubCategoryList(categoryId));
    }

    @ApiOperation(value = "[카테고리] 카테고리별 게시글 조회")
    @GetMapping("/{category-id}/posts")
    public ResponseEntity<PostListByCategoryResponseDto> selectPostByCategory(@PathVariable("category-id") Long categoryId, StatesEnum states) {
        return ResponseEntity.ok(categoryService.selectPostByCategory(categoryId, states));
    }

}
