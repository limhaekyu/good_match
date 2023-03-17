package com.example.good_match.domain.main.controller;

import com.example.good_match.domain.main.dto.response.MainPostsByCategoryResponseDto;
import com.example.good_match.domain.main.dto.response.MainResponseDto;
import com.example.good_match.domain.main.service.MainService;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.util.StatesEnum;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainController {

    private final MainService mainService;

    @ApiOperation(value = "[메인] 메인화면 데이터 조회")
    @GetMapping("")
    public ApiResponseDto<MainResponseDto> selectMainInfo() {
        return mainService.selectMainInfo();
    }

    /*
        카테고리별 게시글 조회 10개씩 끊어서 보내기
     */
    @ApiOperation(value = "[메인] 카테고리별 게시글 조회 (10개씩)")
    @GetMapping("/posts")
    @ResponseStatus(value = HttpStatus.OK)
    public MainPostsByCategoryResponseDto selectMainPostsByCategory(@RequestParam("subCategoryId") Long subCategoryId, @RequestParam("pageNumber") Integer pageNumber) {
        return mainService.selectMainPostsBySubCategory(subCategoryId, pageNumber);
    }

}
