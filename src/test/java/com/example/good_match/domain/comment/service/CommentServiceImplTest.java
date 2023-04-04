package com.example.good_match.domain.comment.service;

import com.example.good_match.domain.comment.dto.request.InsertCommentRequestDto;
import com.example.good_match.domain.comment.dto.request.UpdateCommentRequestDto;
import com.example.good_match.domain.comment.model.Comment;
import com.example.good_match.domain.comment.repository.CommentRepository;
import com.example.good_match.domain.member.model.Authority;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.domain.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private PostService postService;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    @DisplayName("[댓글] 댓글 작성 테스트")
    void insertComment() {
        // Setup
        Long memberId = 1L;
        Member member = Member.builder()
                .id(memberId)
                .build();

        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .build();

        String contents = "테스트 댓글";
        InsertCommentRequestDto insertCommentRequest = InsertCommentRequestDto.builder()
                .postId(postId)
                .contents(contents)
                .build();

        when(memberService.findMemberById(memberId)).thenReturn(member);
        when(postService.findPostById(insertCommentRequest.getPostId())).thenReturn(post);

        commentService.insertComment(insertCommentRequest, memberId);

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("[댓글] 댓글 수정 테스트")
    void updateComment() {
        // Setup
        Long memberId = 1L;
        Long commentId = 2L;
        Long postId = 3L;
        String contents = "수정후 댓글";

        Member member = Member.builder()
                .id(memberId)
                .build();
        Post post = Post.builder()
                .id(postId)
                .build();
        Comment comment = Comment.builder()
                .id(commentId)
                .contents("수정전 댓글")
                .member(member)
                .post(post)
                .build();
        UpdateCommentRequestDto updateCommentRequest = UpdateCommentRequestDto.builder()
                .contents(contents)
                .build();

        when(memberService.findMemberById(memberId)).thenReturn(member);
        when(commentRepository.findById(commentId)).thenReturn(Optional.ofNullable(comment));

        commentService.updateComment(memberId, commentId, updateCommentRequest);

        verify(commentRepository).findById(commentId);
        verify(memberService).findMemberById(memberId);

        assertEquals(Objects.requireNonNull(comment).getContents(), contents);
    }

    @Test
    @DisplayName("[댓글] 댓글 삭제 테스트")
    void cancelComment() {
        // Setup
        Long memberId = 1L;
        Long commentId = 2L;
        Long postId = 3L;
        String contents = "수정후 댓글";

        Member member = Member.builder()
                .id(memberId)
                .authority(Authority.ROLE_USER)
                .build();
        Post post = Post.builder()
                .id(postId)
                .build();
        Comment comment = Comment.builder()
                .id(commentId)
                .contents("수정전 댓글")
                .member(member)
                .post(post)
                .build();

        when(memberService.findMemberById(memberId)).thenReturn(member);
        when(commentRepository.findById(commentId)).thenReturn(Optional.ofNullable(comment));

        commentService.cancelComment(memberId, commentId);

        verify(commentRepository).delete(Objects.requireNonNull(comment));
    }
}