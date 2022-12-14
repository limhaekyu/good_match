package com.example.good_match.domain.board.dto.request;

import com.example.good_match.global.util.StatesEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateBoardRequestDto {

    @Nullable
    private String title;

    @Nullable
    private String contents;

    @Nullable
    private StatesEnum states;
}
