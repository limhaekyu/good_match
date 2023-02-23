package com.example.good_match.domain.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InsertCommentRequestDto {
    private String contents;

    private Long postId;
}
