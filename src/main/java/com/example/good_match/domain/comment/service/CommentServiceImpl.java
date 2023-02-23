package com.example.good_match.domain.comment.service;

import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.domain.comment.dto.request.InsertCommentRequestDto;
import com.example.good_match.domain.comment.dto.request.UpdateCommentRequestDto;
import com.example.good_match.domain.comment.model.Comment;
import com.example.good_match.domain.comment.repository.CommentRepository;
import com.example.good_match.domain.member.model.Authority;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final MemberService memberService;
    private final PostRepository postRepository;

    @Transactional
    public ApiResponseDto insertComment(InsertCommentRequestDto insertCommentRequestDto, User user) {
        try {
            commentRepository.save(
                    Comment.builder()
                            .contents(insertCommentRequestDto.getContents())
                            .member(memberService.findMemberByJwt(user))
                            .post(postRepository.findById(insertCommentRequestDto.getPostId()).orElseThrow(
                                    ()->new IllegalArgumentException("게시글을 찾을 수 없습니다.")))
                            .build()
            );
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "댓글 작성 성공!");
        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "댓글 작성 실패! "+e.getMessage());
        }
    }

    @Transactional
    public ApiResponseDto updateComment(User user, Long commentId, UpdateCommentRequestDto updateCommentRequestDto) {
        try {
            Comment comment = this.findCommentById(commentId);
            if (comment.getMember().equals(memberService.findMemberByJwt(user))){
                comment.update(
                        updateCommentRequestDto.getContents()
                );
            }
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "댓글 수정 성공!");
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "댓글 수정 실패! " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponseDto cancelComment(User user, Long commentId) {
        try {
            Comment comment = this.findCommentById(commentId);
            Member member = memberService.findMemberByJwt(user);

            if (member.getAuthority() == Authority.ROLE_ADMIN || member.equals(comment.getMember())) {
                commentRepository.delete(comment);
                return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "댓글 삭제 성공!");
            } else {
                return ApiResponseDto.of(ResponseStatusCode.FORBIDDEN.getValue(), "댓글 삭제 실패! (삭제 권한이 없습니다)");
            }

        } catch(Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "댓글 삭제 실패! " + e.getMessage());
        }
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));
    }

}
