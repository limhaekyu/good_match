package com.example.good_match.domain.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class InsertCommentRequestDto {
    private String contents;

    private Long postId;
}
