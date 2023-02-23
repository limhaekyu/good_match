package com.example.good_match.domain.category.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InsertCategoryRequestDto {
    private String title;
    private List<String> subCategories;
}
