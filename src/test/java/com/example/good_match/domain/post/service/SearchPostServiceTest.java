package com.example.good_match.domain.post.service;

import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.dto.response.SearchPostResponseDto;
import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.domain.post.repository.PostRepositorySupport;
import com.example.good_match.global.exception.NullSearchKeywordException;
import com.example.good_match.global.util.StatesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchPostServiceTest {

    @Mock
    private PostRepositorySupport postRepositorySupport;

    @InjectMocks
    private SearchPostServiceImpl searchPostService;

    @Test
    @DisplayName(" [검색 기능 테스트] 키워드 검색 Querydsl 사용 ")
    void searchPostsDsl() throws NullSearchKeywordException {

        // Set up data
        StatesEnum city = StatesEnum.NATIONWIDE;
        Long categoryId = 1L;
        Long categoryId2 = 2L;
        String keyword = "Test";
        Category category = Category.builder()
                .id(categoryId)
                .build();
        Category category2 = Category.builder()
                .id(categoryId2)
                .build();
        Member member = Member.builder()
                .id(1L)
                .build();
        List<Post> posts = Arrays.asList(
                Post.builder()
                        .id(1L).title("Test post 1").category(category).states(StatesEnum.SEJONG).contents("Test content1").member(member)
                        .build(),
                Post.builder()
                        .id(2L).title("Test post 2").category(category).states(StatesEnum.BUSAN).contents("Test content2").member(member)
                        .build(),
                Post.builder()
                        .id(3L).title("Test post 3").category(category2).states(StatesEnum.SEJONG).contents("Test content3").member(member)
                        .build()
        );

        // Repository return
        when(postRepositorySupport.searchByKeyword(city, null, keyword)).thenReturn(posts);
        when(postRepositorySupport.searchByKeyword(city,null, "2")).thenReturn(Collections.singletonList(posts.get(1)));
        when(postRepositorySupport.searchByKeyword(StatesEnum.SEJONG, null, keyword)).thenReturn(Arrays.asList(posts.get(0), posts.get(2)));
        when(postRepositorySupport.searchByKeyword(city, categoryId, keyword)).thenReturn(Arrays.asList(posts.get(0), posts.get(1)));
        when(postRepositorySupport.searchByKeyword(StatesEnum.SEJONG, categoryId, keyword)).thenReturn(Collections.singletonList(posts.get(0)));

        // Case 1: 전국, 전체 카테고리
        List<SearchPostResponseDto> result1 = searchPostService.searchPosts(city, null, keyword);
        assertEquals(3, result1.size());
        assertEquals("Test post 1", result1.get(0).getTitle());
        assertEquals("Test post 2", result1.get(1).getTitle());
        assertEquals("Test post 3", result1.get(2).getTitle());

        List<SearchPostResponseDto> result11 = searchPostService.searchPosts(city, null, "2");
        assertEquals(3, result1.size());
        assertEquals("Test post 2", result11.get(0).getTitle());

        // Case 2: 전국, 특정 카테고리 검색
        List<SearchPostResponseDto> result2 = searchPostService.searchPosts(city, categoryId, keyword);
        assertEquals(2, result2.size());
        assertEquals("Test post 1", result2.get(0).getTitle());
        assertEquals("Test post 2", result2.get(1).getTitle());

        // Case 3 : 특정 지역, 전체 카테고리 검색
        List<SearchPostResponseDto> result3 = searchPostService.searchPosts(StatesEnum.SEJONG, null, keyword);
        assertEquals(2, result3.size());
        assertEquals("Test post 1", result3.get(0).getTitle());
        assertEquals("Test post 3", result3.get(1).getTitle());

        // Case 4 : 특정 지역, 특정 카테고리 검색
        List<SearchPostResponseDto> result4 = searchPostService.searchPosts(StatesEnum.SEJONG, categoryId, keyword);
        assertEquals(1, result4.size());
        assertEquals("Test post 1", result4.get(0).getTitle());
    }
}