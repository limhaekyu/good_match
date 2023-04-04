package com.example.good_match.domain.main.service;

import com.example.good_match.domain.category.dto.response.CategoryInfoDto;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.domain.main.dto.response.*;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.dto.response.PostWriterDto;
import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.global.util.StatesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private MainServiceImpl mainService;

    @Test
    @DisplayName("[메인] 메인 데이터 조회 테스트")
    void selectMainInfo() {
        List<Category> categories = new ArrayList<>();
        List<SubCategory> subCategories = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        List<CategoryResponseDto> categoryResponseList = new ArrayList<>();
        List<MainSubCategoryDto> mainSubCategoryList = new ArrayList<>();
        List<PostResponseDto> postResponseList = new ArrayList<>();

        for (int i=1; i<3; i++) {
            subCategories.add(SubCategory.builder()
                    .id((long) i).subCategoryTitle("서브 카테고리 " + i)
                    .build());
        }

        for (int i=1; i<4; i++) {
            categories.add(Category.builder()
                            .id((long) i).title("카테고리 " + i).subCategories(subCategories)
                    .build());
        }

        for (int i=1 ; i<20; i++) {
            posts.add(Post.builder()
                            .id((long) i).title("테스트 제목 " + i).contents("테스트 내용 " +i).states(StatesEnum.ULSAN)
                    .member(Member.builder().id(1L).build()).category(categories.get(0)).subCategory(subCategories.get(0))
                    .build());
        }

        for (Category category : categories) {
            for (SubCategory subCategory : category.getSubCategories()) {
                mainSubCategoryList.add(MainSubCategoryDto.builder()
                                .subCategoryId(subCategory.getId()).subCategoryTitle(subCategory.getSubCategoryTitle())
                        .build());
            }
            categoryResponseList.add(CategoryResponseDto.builder()
                            .id(category.getId()).title(category.getTitle()).subCategories(mainSubCategoryList)
                    .build());
        }


        List<Post> sevenPosts = new ArrayList<>();
        for (int i=18; i>11; i--) {
            sevenPosts.add(Post.builder()
                            .id(posts.get(i).getId()).title(posts.get(i).getTitle())
                            .states(posts.get(i).getStates()).member(posts.get(i).getMember())
                            .category(posts.get(i).getCategory()).subCategory(posts.get(i).getSubCategory())
                    .build());
        }

        for (Post post : sevenPosts) {
            postResponseList.add(PostResponseDto.builder()
                    .id(post.getId()).title(post.getTitle()).states(post.getStates()).updatedAt(LocalDateTime.now())
                    .build());
        }

        MainResponseDto mainResponse = MainResponseDto.builder()
                .categories(categoryResponseList)
                .posts(postResponseList)
                .build();

        when(categoryRepository.findAll()).thenReturn(categories);
        when(postRepository.findRecentPosts(Pageable.ofSize(7))).thenReturn(sevenPosts);

        MainResponseDto result = mainService.selectMainInfo();

        assertEquals(mainResponse.getPosts().size(), result.getPosts().size());
        assertEquals(mainResponse.getCategories().size(), result.getCategories().size());
        assertEquals(mainResponse.getCategories().get(0).getId(), result.getCategories().get(0).getId());
        assertEquals(mainResponse.getPosts().get(0).getId(), result.getPosts().get(0).getId());
        verify(categoryRepository).findAll();
        verify(postRepository).findRecentPosts(Pageable.ofSize(7));

    }

    @Test
    @DisplayName("[메인] 카테고리별 게시글 조회 (10개 페이징)")
    void selectMainPostsBySubCategory() {

        List<Category> categories = new ArrayList<>();
        List<SubCategory> subCategories = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        for (int i=1; i<3; i++) {
            subCategories.add(SubCategory.builder()
                    .id((long) i).subCategoryTitle("서브 카테고리 " + i)
                    .build());
        }

        for (int i=1; i<4; i++) {
            categories.add(Category.builder()
                    .id((long) i).title("카테고리 " + i).subCategories(subCategories)
                    .build());
        }

        for (int i=1 ; i<20; i++) {
            posts.add(Post.builder()
                    .id((long) i).title("테스트 제목 " + i).contents("테스트 내용 " +i).states(StatesEnum.ULSAN)
                    .member(Member.builder().id(1L).name("LIM").build()).category(categories.get(0)).subCategory(subCategories.get(0))
                    .build());
        }

        Long subCategoryId = 1L;
        Integer pageNumber = 1;

        List<PostsByCategoryResponseDto> returnPosts = new ArrayList<>();

        for (int i=18; i>8; i-- ) {
            PostWriterDto writer = new PostWriterDto(posts.get(i).getMember().getId(), posts.get(i).getMember().getName());
            CategoryInfoDto categoryInfo = new CategoryInfoDto(posts.get(i).getCategory().getId(), posts.get(i).getCategory().getTitle(),
                    posts.get(i).getSubCategory().getId(), posts.get(i).getSubCategory().getSubCategoryTitle());

            returnPosts.add(PostsByCategoryResponseDto.builder()
                    .postId(posts.get(i).getId()).title(posts.get(i).getTitle()).contents(posts.get(i).getContents()).updatedAt(posts.get(i).getUpdatedAt())
                    .postWriter(writer).categoryInfo(categoryInfo)
                    .build());
        }

        Page<Post> postPage = new PageImpl<>(Arrays.asList(posts.get(18), posts.get(17), posts.get(16),
                posts.get(15), posts.get(14), posts.get(13), posts.get(12), posts.get(11), posts.get(10), posts.get(9)));

        MainPostsByCategoryResponseDto mainPostsByCategoryResponse = MainPostsByCategoryResponseDto.builder()
                .posts(returnPosts)
                .page(pageNumber)
                .build();

        when(categoryService.findSubCategoryById(subCategoryId)).thenReturn(subCategories.get(0));
        when(postRepository.findAllBySubCategory(eq(subCategories.get(0)), any(Pageable.class))).thenReturn(postPage);

        MainPostsByCategoryResponseDto result = mainService.selectMainPostsBySubCategory(subCategoryId, pageNumber);

        assertEquals(mainPostsByCategoryResponse.getPosts().size(), result.getPosts().size());
        verify(categoryService).findSubCategoryById(subCategoryId);
        verify(postRepository).findAllBySubCategory(eq(subCategories.get(0)), any(Pageable.class));
    }
}