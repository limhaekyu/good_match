package com.example.good_match.domain.main.service;

import com.example.good_match.domain.board.domain.Board;
import com.example.good_match.domain.board.repository.BoardRepository;
import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.domain.main.dto.response.*;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import com.example.good_match.global.util.StatesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    private final CategoryService categoryService;

    /*
        메인 데이터 반환 로직
    */
    @Override
    public ApiResponseDto<MainResponseDto> selectMainInfo(StatesEnum states) {
        try {
            MainResponseDto mainResponse = MainResponseDto.builder()
                    .categories(selectCategoryList())
                    .boards(selectBoardsByCategory(states, categoryService.findCategoryById(0L)))
                    .build();
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), " 메인 조회 성공! " ,mainResponse);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.INTERNAL_SERVER_ERROR.getValue(), e.getMessage());
        }
    }

    /*
        카테고리별 게시글 데이터 반환시키는 로직 필요
    */

    private List<BoardResponseDto> selectBoardsByCategory(StatesEnum states, Category category) {
        List<Board> boards = boardRepository.findAllByStatesAndCategory(states, category);
        List<BoardResponseDto> mainBoards = new ArrayList<>();
        for (Board board : boards) {
            mainBoards.add(BoardResponseDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .boardStatus(board.getBoardStatus())
                        .states(board.getStates())
                        .updatedAt(board.getUpdatedAt())
                    .build());
        }
        return mainBoards;
    }

    private List<CategoryResponseDto> selectCategoryList(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDto> mainCategories = new ArrayList<>();
        for (Category category : categories) {
            mainCategories.add(CategoryResponseDto.builder()
                            .id(category.getId())
                            .title(category.getTitle())
                            .subCategories(makeDtoSubCategory(category.getSubCategories()))
                    .build());
        }
        return mainCategories;
    }

    private List<MainSubCategoryDto> makeDtoSubCategory(List<SubCategory> subCategories) {
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
