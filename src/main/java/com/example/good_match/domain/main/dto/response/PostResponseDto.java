package com.example.good_match.domain.main.dto.response;

import com.example.good_match.global.util.StatesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostResponseDto {

    private Long id;

    private String title;

    private StatesEnum states;

    private LocalDateTime updatedAt;
}
