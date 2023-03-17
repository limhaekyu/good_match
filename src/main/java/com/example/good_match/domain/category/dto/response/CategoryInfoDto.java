package com.example.good_match.domain.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CategoryInfoDto {
    private Long categoryId;
    private String categoryTitle;
    private Long subCategoryId;
    private String subCategoryTitle;
}
