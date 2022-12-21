package com.example.good_match.domain.game.dto.response;

import com.example.good_match.domain.game.domain.GameStatus;
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
public class SelectGameDetailResponseDto {
    private String title;

    private String contents;

    private StatesEnum states;

    private GameStatus gameStatus;

    private Timestamp updatedAt;

    private Long memberId;

    private String memberName;
}
