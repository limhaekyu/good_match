package com.example.good_match.domain.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MainSubCategoryDto {
    private Long subCategoryId;
    private String subCategoryTitle;
}
