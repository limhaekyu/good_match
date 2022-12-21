package com.example.good_match.domain.game.dto.request;

import com.example.good_match.global.util.StatesEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddGameRequestDto {
    private String title;

    private String contents;

    private StatesEnum states;
}
