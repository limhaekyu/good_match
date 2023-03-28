package com.example.good_match.domain.post.service;

import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.dto.response.SearchPostResponseDto;
import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.global.exception.NullSearchKeywordException;
import com.example.good_match.global.util.StatesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchPostServiceImpl implements SearchPostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;

    @Override
    @Transactional(readOnly = true)
    public List<SearchPostResponseDto> searchPosts(StatesEnum city, Long categoryId, String keyword) throws NullSearchKeywordException {

        List<Post> posts = new ArrayList<>();

        if (keyword == null) {
            throw new NullSearchKeywordException("검색어를 입력해주세요.");
        }

        if (isNationWide(city) && isEveryCategory(categoryId)) {
            // 전체
            posts = postRepository.findByTitleContaining(keyword);
        } else if (isNationWide(city) && !isEveryCategory(categoryId)) {
            // 카테고리별
            posts = postRepository.findByCategoryAndTitleContaining(categoryService.findCategoryById(categoryId), keyword);
        } else if (!isNationWide(city) && isEveryCategory(categoryId)) {
            // 특정지역
            posts = postRepository.findByStatesAndTitleContaining(city, keyword);
        } else if (!isNationWide(city) && !isEveryCategory(categoryId)) {
            // 카테고리,지역
            posts = postRepository.findByStatesAndCategoryAndTitleContaining(city, categoryService.findCategoryById(categoryId), keyword);
        }
        return posts.stream().map(SearchPostResponseDto::new).collect(Collectors.toList());
    }

    private boolean isNationWide(StatesEnum city) {
        return city == StatesEnum.NATIONWIDE;
    }

    private boolean isEveryCategory(Long categoryId) {
        return categoryId == null;
    }


}
