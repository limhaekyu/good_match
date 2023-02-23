package com.example.good_match.domain.board.dto.request;

import com.example.good_match.global.util.StatesEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddBoardRequestDto {
    private String title;

    private String contents;

    private StatesEnum states;

    private Long categoryId;

    private Long subCategoryId;
}
