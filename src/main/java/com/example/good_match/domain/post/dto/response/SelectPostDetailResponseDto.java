package com.example.good_match.domain.post.dto.response;

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
public class SelectPostDetailResponseDto {
    private String title;

    private String contents;

    private StatesEnum states;

    private LocalDateTime updatedAt;

    private Long memberId;

    private String memberName;
}
