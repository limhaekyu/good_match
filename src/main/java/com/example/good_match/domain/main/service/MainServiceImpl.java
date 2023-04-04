package com.example.good_match.domain.main.service;

import com.example.good_match.domain.category.dto.response.CategoryInfoDto;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.domain.main.dto.response.*;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.dto.response.PostWriterDto;
import com.example.good_match.domain.post.mapper.PostMapper;
import com.example.good_match.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    @Override
    public MainResponseDto selectMainInfo() {
        try {
            return MainResponseDto.builder()
                    .categories(selectCategoryList())
                    .posts(selectRecentPosts())
                    .build();
        } catch (Exception e) {
            throw new IllegalArgumentException("메인 데이터 반환 실패! " + e.getMessage());
        }
    }

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

    private List<PostResponseDto> selectRecentPosts() {
        List<Post> posts = postRepository.findRecentPosts(Pageable.ofSize(7));
        return PostMapper.INSTANCE.toDtoList(posts);
    }

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
