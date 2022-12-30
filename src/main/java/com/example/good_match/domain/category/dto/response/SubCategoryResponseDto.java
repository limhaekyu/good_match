package com.example.good_match.domain.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SubCategoryResponseDto {
    private Long subCategoryId;
    private String subCategoryTitle;
}
