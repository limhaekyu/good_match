package com.example.good_match.domain.comment.service;

import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.domain.comment.dto.request.InsertCommentRequestDto;
import com.example.good_match.domain.comment.dto.request.UpdateCommentRequestDto;
import com.example.good_match.domain.comment.model.Comment;
import com.example.good_match.domain.comment.repository.CommentRepository;
import com.example.good_match.domain.member.model.Authority;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.domain.post.service.PostService;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.security.auth.message.AuthException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final MemberService memberService;

    private final PostService postService;

    @Transactional
    public void insertComment(InsertCommentRequestDto insertCommentRequestDto, Long memberId) {
        try {
            commentRepository.save(
                    Comment.builder()
                            .contents(insertCommentRequestDto.getContents())
                            .member(memberService.findMemberById(memberId))
                            .post(postService.findPostById(insertCommentRequestDto.getPostId()))
                            .build()
            );
        } catch (Exception e){
            throw new IllegalArgumentException("댓글 작성 실패! " + e.getMessage());
        }
    }

    @Transactional
    public void updateComment(Long memberId, Long commentId, UpdateCommentRequestDto updateCommentRequestDto) {
        try {
            Comment comment = findCommentById(commentId);
            if (comment.eqMember(memberService.findMemberById(memberId))){
                comment.update(updateCommentRequestDto.getContents());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("댓글 수정 실패! " + e.getMessage());
        }
    }

    @Transactional
    public void cancelComment(Long memberId, Long commentId) {
        try {
            Comment comment = findCommentById(commentId);
            Member member = memberService.findMemberById(memberId);

            if (member.isAdmin() || comment.eqMember(member)) {
                commentRepository.delete(comment);
            } else {
                throw new AuthException("댓글 삭제 실패!, 권한이 없습니다. ");
            }
        } catch(Exception e) {
            throw new IllegalArgumentException("댓글 삭제 실패! " + e.getMessage());
        }
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다."));
    }

}
