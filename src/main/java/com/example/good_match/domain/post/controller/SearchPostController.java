package com.example.good_match.domain.post.controller;

import com.example.good_match.domain.post.dto.response.SearchPostResponseDto;
import com.example.good_match.domain.post.service.SearchPostService;
import com.example.good_match.global.util.StatesEnum;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/search")
public class SearchPostController {

    private final SearchPostService searchPostService;

    /*
        검색 조건
        1. 지역 <전국 or 최대 3개 선택>
        2. 카테고리, 서브카테고리 선택
        3. 제목별 ( 정규식 )
        4. 작성자 별 (정규식, 사용자 이름)
    */
    @ApiOperation(value = "[게시글] 게시글 검색")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<SearchPostResponseDto> searchPosts(@RequestParam(value = "city", required = true) StatesEnum city,
                                            @RequestParam(value = "categoryId", required = false) Long categoryId,
                                            @RequestParam(value="keyword", required = true) String keyword) {
        return searchPostService.searchPosts(city, categoryId, keyword);
    }
}
