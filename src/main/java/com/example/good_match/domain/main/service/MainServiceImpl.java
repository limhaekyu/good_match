package com.example.good_match.domain.main.service;

import com.example.good_match.domain.category.dto.response.CategoryInfoDto;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.repository.SubCategoryRepository;
import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.domain.main.dto.response.*;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.dto.response.PostWriterDto;
import com.example.good_match.domain.post.mapper.PostMapper;
import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import com.example.good_match.global.util.StatesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    private final CategoryService categoryService;

    /*
        메인 데이터 반환 로직
        카테고리, 최신 게시글 10개
    */
    @Transactional(readOnly = true)
    @Override
    public ApiResponseDto<MainResponseDto> selectMainInfo() {
        try {
            MainResponseDto mainResponse = MainResponseDto.builder()
                    .categories(selectCategoryList())
                    .posts(selectRecentPosts())
                    .build();
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), " 메인 조회 성공! " ,mainResponse);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.INTERNAL_SERVER_ERROR.getValue(), e.getMessage());
        }
    }
    /*
        메인페이지 카테고리별 Paging 된 게시글 조회
     */
    @Transactional(readOnly = true)
    @Override
    public MainPostsByCategoryResponseDto selectMainPostsBySubCategory(Long subCategoryId, Integer pageNumber) {
        Page<Post> postPage = postRepository.findAllBySubCategory(
                categoryService.findSubCategoryById(subCategoryId),
                PageRequest.of(pageNumber, 10, Sort.by("id").descending()));
        return MainPostsByCategoryResponseDto.builder()
                .posts(makePostsByCategoryDto(postPage))
                .page(pageNumber)
                .build();
    }

    /*
        최근 게시글
     */
    private List<PostResponseDto> selectRecentPosts() {
        List<Post> posts = postRepository.findRecentPosts(Pageable.ofSize(7));
        return PostMapper.INSTANCE.toDtoList(posts);
    }

    /*
        카테고리/sub 카테고리 별 게시글 조회
     */
    private List<PostsByCategoryResponseDto> makePostsByCategoryDto(Page<Post> posts) {
        List<PostsByCategoryResponseDto> postsDto = new ArrayList<>();
        for (Post post : posts) {
            postsDto.add(PostsByCategoryResponseDto.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .contents(post.getContents())
                            .updatedAt(post.getUpdatedAt())
                            .postWriter(PostWriterDto.builder()
                                    .memberId(post.getMember().getId())
                                    .name(post.getMember().getName())
                                    .build())
                            .categoryInfo(CategoryInfoDto.builder()
                                    .categoryId(post.getCategory().getId())
                                    .categoryTitle(post.getCategory().getTitle())
                                    .subCategoryId(post.getSubCategory().getId())
                                    .subCategoryTitle(post.getSubCategory().getSubCategoryTitle())
                                    .build())
                    .build());
        }
        return postsDto;
    }

    /*
        카테고리 리스트 조회
     */
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

    /*
        sub 카테고리 리스트 생성
     */
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
