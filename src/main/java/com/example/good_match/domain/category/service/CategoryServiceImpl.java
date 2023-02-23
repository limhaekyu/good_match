package com.example.good_match.domain.category.service;

import com.example.good_match.domain.board.domain.Board;
import com.example.good_match.domain.board.repository.BoardRepository;
import com.example.good_match.domain.category.dto.request.InsertCategoryRequestDto;
import com.example.good_match.domain.category.dto.response.BoardResponseDto;
import com.example.good_match.domain.category.dto.response.BoardsByCategoryResponseDto;
import com.example.good_match.domain.category.dto.response.CategoryResponseDto;
import com.example.good_match.domain.category.dto.response.SubCategoryResponseDto;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.repository.SubCategoryRepository;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import com.example.good_match.global.util.StatesEnum;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final BoardRepository boardRepository;


    /*
        카테고리 등록
    */

    @Override
    public ApiResponseDto insertCategory(User user, InsertCategoryRequestDto insertCategoryRequest) {
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
                return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "카테고리 등록에 성공했습니다.");
            } else {
                return ApiResponseDto.of(ResponseStatusCode.REGISTERED.getValue(), "이미 등록된 카테고리입니다.");
            }
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.INTERNAL_SERVER_ERROR.getValue(), "카테고리 등록에 실패했습니다. " + e.getMessage());
        }
    }

    /*
        전체 카테고리 리스트 호출
    */
    public ApiResponseDto<List<CategoryResponseDto>> selectCategoryList() {
        try {
            List<CategoryResponseDto> categoryResponseList = categoryRepository.findAll().stream().map(this::response).toList();

            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "카테고리 조회 성공", categoryResponseList);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "카테고리 조회를 실패했습니다 " + e.getMessage());
        }
    }

    /*
        카테고리별 서브 카테고리 호출
    */

    public ApiResponseDto<List<SubCategoryResponseDto>> selectSubCategoryList(Long categoryId) {
        try {

            List<SubCategoryResponseDto> subCategoryResponselist = subCategoryRepository.findAllByCategoryId(categoryId).stream().map(this::subResponse).toList();
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "서브 카테고리 조회 성공", subCategoryResponselist);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "서브 카테고리 조회 실패 "+e.getMessage());
        }
    }

    /*
        카테고리 ID로 카테고리 찾기
    */
    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()-> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
    }


    /*
        카테고리별 게시글 호출
    */
    @Override
    public ApiResponseDto<BoardsByCategoryResponseDto> selectBoardByCategory(Long categoryId, StatesEnum states) {
        try {
            Category category = findCategoryById(categoryId);
            BoardsByCategoryResponseDto boardsByCategory = BoardsByCategoryResponseDto.builder()
                    .categoryId(categoryId)
                    .categoryTitle(category.getTitle())
                    .boards(selectBoards(states, category))
                    .build();
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "해당 카테고리 게시글 조회 성공! ", boardsByCategory);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.INTERNAL_SERVER_ERROR.getValue(), e.getMessage());
        }
    }

    private List<BoardResponseDto> selectBoards(StatesEnum states, Category category) {
        List<BoardResponseDto> boardResponses = new ArrayList<>();
        List<Board> boards = boardRepository.findAllByStatesAndCategory(states, category);
        for (Board board : boards) {
            BoardResponseDto boardResponse = BoardResponseDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .states(board.getStates())
                    .boardStatus(board.getBoardStatus())
                    .updatedAt(board.getUpdatedAt())
                    .build();
            boardResponses.add(boardResponse);
        }
        return boardResponses;
    }

    // 카테고리 별 서브 카테고리 매핑
    private CategoryResponseDto response(Category category) {
        List<SubCategoryResponseDto> subCategoryResponseList = new ArrayList<>();

        if(category.getSubCategories() != null) {
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
