package com.example.good_match.domain.post.service;

import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.repository.SubCategoryRepository;
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

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    /*
        [매칭] 게임 게시글 등록
     */
    @Transactional
    public ApiResponseDto insertPost(AddPostRequestDto addPostRequestDto, Long memberId) {
        try{
            postRepository.save(Post.builder()
                    .title(addPostRequestDto.getTitle())
                    .contents(addPostRequestDto.getContents())
                    .states(addPostRequestDto.getStates())
                    .member(memberService.findMemberById(memberId))
                    .category(categoryRepository.findById(addPostRequestDto.getCategoryId()).orElseThrow(()->new IllegalArgumentException("없는 카테고리입니다.")))
                    .subCategory(subCategoryRepository.findById(addPostRequestDto.getSubCategoryId()).orElseThrow(()->new IllegalArgumentException("없는 서브 카테고리입니다.")))
                    .build());
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글 등록을 성공했습니다. ");
        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글 등록에 실패했습니다. " + e.getMessage());
        }
    }


    /*
        [매칭] 게임 게시글 상세 조회
     */
    @Transactional
    public ApiResponseDto selectPostDetail(Long id) {
        try {
            Post post = findPostById(id);

            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글 상세 조회에 성공했습니다.",
                    SelectPostDetailResponseDto.builder()
                            .title(post.getTitle())
                            .contents(post.getContents())
                            .states(post.getStates())
                            .updatedAt(post.getUpdatedAt())
                            .memberId(post.getMember().getId())
                            .memberName(post.getMember().getName())
                            .build()
            );
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글 상세 조회에 실패했습니다. " + e.getMessage());
        }
    }


    /*
        [매칭] 게임 게시글 삭제
     */

    @Transactional
    public ApiResponseDto deletePost(Long id, Long memberId) {
        try {
            Post post = findPostById(id);
            Member member = memberService.findMemberById(memberId);
            if (member == post.getMember() || member.getAuthority().equals("ROLE_ADMIN")) {
                postRepository.delete(post);
                return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글을 삭제했습니다.");
            } else {
                return ApiResponseDto.of(ResponseStatusCode.UNAUTHORIZED.getValue(), "해당 사용자의 게임 메칭 게시글이 아닙니다.");
            }
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글을 삭제했습니다.. " + e.getMessage());
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
    public ApiResponseDto updatePost(Long id, UpdatePostRequestDto updatePostRequestDto, Long memberId) {
        try {
            Post post = findPostById(id);
            Member member = memberService.findMemberById(memberId);
            if (member == post.getMember() || member.getAuthority().equals("ROLE_ADMIN")) {

                post.updatePost(updatePostRequestDto.getTitle(), updatePostRequestDto.getContents(), updatePostRequestDto.getStates());
                postRepository.save(post);

                return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글 수정을 완료했습니다.");
            } else {
                return ApiResponseDto.of(ResponseStatusCode.UNAUTHORIZED.getValue(), "수정 권한이 없습니다. ");
            }

        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글 수정에 실패했습니다. " + e.getMessage());
        }
    }

}
