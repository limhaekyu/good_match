package com.example.good_match.domain.board.dto.response;

import com.example.good_match.domain.board.domain.BoardStatus;
import com.example.good_match.global.util.StatesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SelectBoardDetailResponseDto {
    private String title;

    private String contents;

    private StatesEnum states;

    private BoardStatus boardStatus;

    private Timestamp updatedAt;

    private Long memberId;

    private String memberName;
}
