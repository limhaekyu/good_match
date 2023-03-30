package com.example.good_match.domain.category.service;

import com.example.good_match.domain.category.dto.request.InsertCategoryRequestDto;
import com.example.good_match.domain.category.dto.response.CategoryResponseDto;
import com.example.good_match.domain.category.dto.response.PostListByCategoryResponseDto;
import com.example.good_match.domain.category.dto.response.SubCategoryResponseDto;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.repository.SubCategoryRepository;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.global.util.StatesEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private SubCategoryRepository subCategoryRepository;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("[카테고리] 카테고리 추가")
    void insertCategory() {
        // Setup
        String title = "풋살/족구";
        List<String> subCategories = Arrays.asList("용병", "동호회");
        InsertCategoryRequestDto insertCategoryRequestDto = InsertCategoryRequestDto.builder()
                .title(title)
                .subCategories(subCategories)
                .build();
        when(categoryRepository.existsByTitle(title)).thenReturn(false);

        // act
        categoryService.insertCategory(insertCategoryRequestDto);

        // assert
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(subCategoryRepository, times(2)).save(any(SubCategory.class));
    }

    @Test
    @DisplayName("[카테고리] 전체 카테고리 조회")
    void selectCategoryList() {
        // setup
        List<SubCategory> subCategories = Arrays.asList(
                SubCategory.builder().id(1L).subCategoryTitle("용병").build(),
                SubCategory.builder().id(2L).subCategoryTitle("동호회").build()
        );
        List<Category> categories = Arrays.asList(
                Category.builder()
                        .id(1L)
                        .title("축구/풋살")
                        .subCategories(subCategories)
                        .build(),
                Category.builder()
                        .id(2L)
                        .title("러닝")
                        .subCategories(subCategories)
                        .build()
                );

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryResponseDto> returnCategories = categoryService.selectCategoryList();
        assertEquals(returnCategories.size(), 2);
        assertEquals(returnCategories.get(0).getTitle(), "축구/풋살");
        assertEquals(returnCategories.get(1).getSubCategories().get(0).getSubCategoryTitle(), "용병");
    }

    @Test
    @DisplayName("[카테고리] 카테고리 별 서브 카테고리 조회")
    void selectSubCategoryList() {
        List<SubCategory> subCategories = Arrays.asList(
                SubCategory.builder().id(1L).subCategoryTitle("용병").build(),
                SubCategory.builder().id(2L).subCategoryTitle("동호회").build()
        );
        List<SubCategory> subCategories2 = Arrays.asList(
                SubCategory.builder().id(1L).subCategoryTitle("번개").build(),
                SubCategory.builder().id(2L).subCategoryTitle("동호회").build()
        );

        when(subCategoryRepository.findAllByCategoryId(1L)).thenReturn(subCategories);
        when(subCategoryRepository.findAllByCategoryId(2L)).thenReturn(subCategories2);

        List<SubCategoryResponseDto> resultSubCategories = categoryService.selectSubCategoryList(1L);
        List<SubCategoryResponseDto> resultSubCategories2 = categoryService.selectSubCategoryList(2L);

        assertEquals(resultSubCategories.get(0).getSubCategoryTitle(), "용병");
        assertEquals(resultSubCategories.get(1).getSubCategoryTitle(), "동호회");
        assertEquals(resultSubCategories2.get(0).getSubCategoryTitle(), "번개");
        assertEquals(resultSubCategories2.get(1).getSubCategoryTitle(), "동호회");
    }

    @Test
    @DisplayName("[카테고리] 카테고리별 게시글 조회")
    void selectPostByCategory() {
        List<SubCategory> subCategories = Arrays.asList(
                SubCategory.builder().id(1L).subCategoryTitle("용병").build(),
                SubCategory.builder().id(2L).subCategoryTitle("동호회").build()
        );
        List<SubCategory> subCategories2 = Arrays.asList(
                SubCategory.builder().id(1L).subCategoryTitle("번개").build(),
                SubCategory.builder().id(2L).subCategoryTitle("동호회").build()
        );
        List<Category> categories = Arrays.asList(
                Category.builder()
                        .id(1L)
                        .title("축구/풋살")
                        .subCategories(subCategories)
                        .build(),
                Category.builder()
                        .id(2L)
                        .title("러닝")
                        .subCategories(subCategories2)
                        .build()
        );
        Member member = Member.builder()
                .id(1L)
                .build();
        List<Post> posts = Arrays.asList(
                Post.builder()
                        .id(1L).title("Test post 1").category(categories.get(0)).states(StatesEnum.SEOUL).contents("Test content1").member(member)
                        .build(),
                Post.builder()
                        .id(2L).title("Test post 2").category(categories.get(1)).states(StatesEnum.SEOUL).contents("Test content2").member(member)
                        .build()
        );
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(categories.get(0)));
        when(categoryRepository.findById(2L)).thenReturn(Optional.ofNullable(categories.get(1)));
        when(postRepository.findAllByStatesAndCategory(StatesEnum.SEOUL, categories.get(0))).thenReturn(
                Collections.singletonList(posts.get(0)));
        when(postRepository.findAllByStatesAndCategory(StatesEnum.SEOUL, categories.get(1))).thenReturn(
                Collections.singletonList(posts.get(1)));

        PostListByCategoryResponseDto resultPostListByCategory = categoryService.selectPostByCategory(1L, StatesEnum.SEOUL);
        PostListByCategoryResponseDto resultPostListByCategory2 = categoryService.selectPostByCategory(2L, StatesEnum.SEOUL);
        assertEquals(resultPostListByCategory.getCategoryId(), 1L );
        assertEquals(resultPostListByCategory.getPosts().size(), 1);
        assertEquals(resultPostListByCategory.getPosts().get(0).getTitle(), posts.get(0).getTitle());
        assertEquals(resultPostListByCategory2.getCategoryId(), 2L );
        assertEquals(resultPostListByCategory2.getPosts().get(0).getTitle(), posts.get(1).getTitle());

    }
}