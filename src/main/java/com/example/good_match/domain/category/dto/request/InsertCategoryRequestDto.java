package com.example.good_match.domain.category.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class InsertCategoryRequestDto {
    private String title;
    private List<String> subCategories;
}
