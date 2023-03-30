package com.example.good_match.domain.post.dto.request;

import com.example.good_match.global.util.StatesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AddPostRequestDto {
    private String title;

    private String contents;

    private StatesEnum states;

    private Long categoryId;

    private Long subCategoryId;
}
