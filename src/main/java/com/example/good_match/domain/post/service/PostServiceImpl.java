package com.example.good_match.domain.post.service;

import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.repository.SubCategoryRepository;
import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.domain.member.model.Authority;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.dto.request.AddPostRequestDto;
import com.example.good_match.domain.post.dto.request.UpdatePostRequestDto;
import com.example.good_match.domain.post.dto.response.SelectPostDetailResponseDto;
import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.message.AuthException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final MemberService memberService;

    private final CategoryService categoryService;

    /*
        [매칭] 게임 게시글 등록
     */
    @Transactional
    public void insertPost(AddPostRequestDto addPostRequestDto, Long memberId) {
        try{
            postRepository.save(Post.builder()
                    .title(addPostRequestDto.getTitle())
                    .contents(addPostRequestDto.getContents())
                    .states(addPostRequestDto.getStates())
                    .member(memberService.findMemberById(memberId))
                    .category(categoryService.findCategoryById(addPostRequestDto.getCategoryId()))
                    .subCategory(categoryService.findSubCategoryById(addPostRequestDto.getSubCategoryId()))
                    .build());
        } catch (Exception e){
            throw new IllegalArgumentException("게시글 등록에 실패했습니다. " + e.getMessage());
        }
    }


    /*
        [매칭] 게임 게시글 상세 조회
     */
    @Transactional(readOnly = true)
    public SelectPostDetailResponseDto selectPostDetail(Long id) {
        try {
            Post post = findPostById(id);

            return SelectPostDetailResponseDto.builder()
                            .title(post.getTitle())
                            .contents(post.getContents())
                            .states(post.getStates())
                            .updatedAt(post.getUpdatedAt())
                            .memberId(post.getMember().getId())
                            .memberName(post.getMember().getName())
                            .build();

        } catch (Exception e) {
            throw new IllegalArgumentException("상세조회에 실패했습니다. " + e.getMessage());
        }
    }


    /*
        [매칭] 게임 게시글 삭제
     */

    @Transactional
    public void deletePost(Long id, Long memberId) {
        try {
            Post post = findPostById(id);
            Member member = memberService.findMemberById(memberId);
            if (post.eqMember(member) || member.isAdmin()) {
                postRepository.delete(post);
            } else {
                throw new AuthException("해당 사용자의 게시글이 아닙니다.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("게시글 삭제 실패!" + e.getMessage());
        }
    }


    /*
        [매칭] 게임 게시글 찾기 (with. index id)
     */

    private Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow( ()-> new IllegalArgumentException("없는 게임 매칭 게시글입니다.") );
    }

    /*
        [매칭] 게임 게시글 수정
     */
    @Transactional
    public void updatePost(Long id, UpdatePostRequestDto updatePostRequestDto, Long memberId) {
        try {
            Post post = findPostById(id);
            Member member = memberService.findMemberById(memberId);
            if (post.eqMember(member) || member.isAdmin()) {
                post.updatePost(updatePostRequestDto.getTitle(), updatePostRequestDto.getContents(), updatePostRequestDto.getStates());
                postRepository.save(post);
            } else {
                throw new AuthException("수정 권한이 없습니다.");
            }
        } catch (Exception e){
            throw new IllegalArgumentException("게시글 수정 실패! " +e.getMessage());
        }
    }

}
