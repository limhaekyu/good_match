package com.example.good_match.domain.main.service;

import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.main.dto.response.CategoryResponseDto;
import com.example.good_match.domain.main.dto.response.MainResponseDto;
import com.example.good_match.domain.main.dto.response.MainSubCategoryDto;
import com.example.good_match.domain.main.dto.response.PostResponseDto;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.mapper.PostMapper;
import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import com.example.good_match.global.util.StatesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    /*
        메인 데이터 반환 로직
        카테고리, 최신 게시글 10개
    */
    @Transactional
    @Override
    public ApiResponseDto<MainResponseDto> selectMainInfo() {
        try {
            MainResponseDto mainResponse = MainResponseDto.builder()
                    .categories(selectCategoryList())
                    .posts(selectCurrentPosts())
                    .build();
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), " 메인 조회 성공! " ,mainResponse);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.INTERNAL_SERVER_ERROR.getValue(), e.getMessage());
        }
    }

    private List<PostResponseDto> selectCurrentPosts() {
        List<Post> posts = postRepository.findCurrentPosts(Pageable.ofSize(7));
        return PostMapper.INSTANCE.toDtoList(posts);
    }

    /*
        카테고리별 게시글 데이터 반환시키는 로직 필요
    */

    private List<PostResponseDto> selectPostsByCategory(StatesEnum states, Category category) {
        List<Post> posts = postRepository.findAllByStatesAndCategory(states, category);
        List<PostResponseDto> mainPosts = new ArrayList<>();
        for (Post post : posts) {
            mainPosts.add(PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .states(post.getStates())
                        .updatedAt(post.getUpdatedAt())
                    .build());
        }
        return mainPosts;
    }

    private List<CategoryResponseDto> selectCategoryList(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDto> mainCategories = new ArrayList<>();
        for (Category category : categories) {
            mainCategories.add(CategoryResponseDto.builder()
                            .id(category.getId())
                            .title(category.getTitle())
                            .subCategories(makeSubCategoryDto(category.getSubCategories()))
                    .build());
        }
        return mainCategories;
    }

    private List<MainSubCategoryDto> makeSubCategoryDto(List<SubCategory> subCategories) {
        List<MainSubCategoryDto> mainSubCategories = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            mainSubCategories.add(MainSubCategoryDto.builder()
                            .subCategoryId(subCategory.getId())
                            .subCategoryTitle(subCategory.getSubCategoryTitle())
                    .build());
        }
        return mainSubCategories;
    }
}
