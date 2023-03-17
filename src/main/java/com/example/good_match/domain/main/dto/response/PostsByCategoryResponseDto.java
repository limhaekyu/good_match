package com.example.good_match.domain.main.dto.response;

import com.example.good_match.domain.category.dto.response.CategoryInfoDto;
import com.example.good_match.domain.post.dto.response.PostWriterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostsByCategoryResponseDto {
    private Long postId;
    private String title;
    private String contents;
    private LocalDateTime updatedAt;
    private PostWriterDto postWriter;
    private CategoryInfoDto categoryInfo;
}
