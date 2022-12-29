package com.example.good_match.domain.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CategoryResponseDto {
    private Long categoryId;
    private String title;
    private List<SubCategoryResponseDto> subCategories;
}
