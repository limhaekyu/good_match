package com.example.good_match.domain.post.dto.response;

import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.global.util.StatesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SearchPostResponseDto {
    private Long postId;
    private String title;
    private StatesEnum states;
    private LocalDateTime updatedAt;
    private PostWriterDto writerInfo;

    public SearchPostResponseDto(Post post) {
        postId = post.getId();
        title = post.getTitle();
        states = post.getStates();
        updatedAt = post.getUpdatedAt();
        writerInfo = PostWriterDto.builder()
                .memberId(post.getMember().getId())
                .name(post.getMember().getName())
                .build();
    }
}
