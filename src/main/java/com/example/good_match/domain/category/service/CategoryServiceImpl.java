package com.example.good_match.domain.category.service;

import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.domain.category.dto.request.InsertCategoryRequestDto;
import com.example.good_match.domain.category.dto.response.PostResponseDto;
import com.example.good_match.domain.category.dto.response.PostListByCategoryResponseDto;
import com.example.good_match.domain.category.dto.response.CategoryResponseDto;
import com.example.good_match.domain.category.dto.response.SubCategoryResponseDto;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.repository.SubCategoryRepository;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import com.example.good_match.global.util.StatesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final PostRepository postRepository;

    /*
        카테고리 등록
    */

    @Transactional
    @Override
    public void insertCategory(InsertCategoryRequestDto insertCategoryRequest) {
        try {

            if (!categoryRepository.existsByTitle(insertCategoryRequest.getTitle())) {
                Category category = Category.builder()
                        .title(insertCategoryRequest.getTitle())
                        .build();
                categoryRepository.save(category);

                for (String subCategory : insertCategoryRequest.getSubCategories()) {
                    subCategoryRepository.save(SubCategory.builder()
                            .subCategoryTitle(subCategory)
                            .category(category)
                            .build());
                }
            } else {
                throw new DuplicateKeyException("이미 등록된 카테고리입니다.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("카테고리 등록에 실패했습니다. " + e.getMessage());
        }
    }

    /*
        전체 카테고리 리스트 호출
    */
    public List<CategoryResponseDto> selectCategoryList() {
        try {
            return categoryRepository.findAll().stream().map(this::response).toList();

        } catch (Exception e) {
            throw new IllegalArgumentException("카테고리 조회 실패! " + e.getMessage());
        }
    }

    /*
        카테고리별 서브 카테고리 호출
    */

    public List<SubCategoryResponseDto> selectSubCategoryList(Long categoryId) {
        try {
            return subCategoryRepository.findAllByCategoryId(categoryId).stream().map(this::subResponse).toList();
        } catch (Exception e) {
            throw new IllegalArgumentException("서브 카테고리 조회 실패! " + e.getMessage());
        }
    }

    /*
        카테고리 ID로 카테고리 찾기
    */
    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()-> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
    }

    @Override
    public SubCategory findSubCategoryById(Long subCategoryId) {
        return subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new IllegalArgumentException("해당 서브카테고리를 찾을 수 없습니다."));
    }

    /*
        카테고리별 게시글 호출
    */
    @Override
    public PostListByCategoryResponseDto selectPostByCategory(Long categoryId, StatesEnum states) {
        try {
            Category category = findCategoryById(categoryId);
            return PostListByCategoryResponseDto.builder()
                    .categoryId(categoryId)
                    .categoryTitle(category.getTitle())
                    .posts(selectPosts(states, category))
                    .build();
        } catch (Exception e) {
            throw new IllegalArgumentException("게시글 조회 실패! " + e.getMessage());
        }
    }

    private List<PostResponseDto> selectPosts(StatesEnum states, Category category) {
        List<PostResponseDto> boardResponses = new ArrayList<>();
        List<Post> posts = postRepository.findAllByStatesAndCategory(states, category);
        for (Post post : posts) {
            PostResponseDto postResponse = PostResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .states(post.getStates())
                    .updatedAt(post.getUpdatedAt())
                    .build();
            boardResponses.add(postResponse);
        }
        return boardResponses;
    }

    // 카테고리 별 서브 카테고리 매핑
    private CategoryResponseDto response(Category category) {
        List<SubCategoryResponseDto> subCategoryResponseList = new ArrayList<>();

        if(!category.isNullSubCategory()) {
            for(SubCategory subCategory : category.getSubCategories()){
                subCategoryResponseList.add(
                        SubCategoryResponseDto.builder()
                            .subCategoryId(subCategory.getId())
                            .subCategoryTitle(subCategory.getSubCategoryTitle())
                            .build());
            }
        }
        return CategoryResponseDto.builder()
                .categoryId(category.getId())
                .title(category.getTitle())
                .subCategories(subCategoryResponseList)
                .build();
    }

    private SubCategoryResponseDto subResponse(SubCategory subCategory) {
        return SubCategoryResponseDto.builder()
                .subCategoryId(subCategory.getId())
                .subCategoryTitle(subCategory.getSubCategoryTitle())
                .build();
    }
}
