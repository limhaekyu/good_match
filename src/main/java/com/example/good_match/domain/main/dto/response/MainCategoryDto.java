package com.example.good_match.domain.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MainCategoryDto {
    private Long id;

    private String title;

    private List<MainSubCategoryDto> subCategoryList;
}
