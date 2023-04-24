package com.example.good_match.domain.post.service;

import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.service.CategoryService;
import com.example.good_match.domain.member.model.Authority;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.dto.request.AddPostRequestDto;
import com.example.good_match.domain.post.dto.request.UpdatePostRequestDto;
import com.example.good_match.domain.post.dto.response.SelectPostDetailResponseDto;
import com.example.good_match.domain.post.repository.PostRepository;
import com.example.good_match.global.util.StatesEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    @DisplayName("[게시글] 글 작성")
    void insertPost() {
        // Set up data
        AddPostRequestDto addPostRequestDto = AddPostRequestDto.builder()
                .title("테스트 제목")
                .contents("테스트 내용")
                .states(StatesEnum.SEOUL)
                .categoryId(1L)
                .subCategoryId(1L)
                .build();
        Long memberId = 1L;
        Long categoryId = 1L;
        Long subCategoryId = 1L;
        Member member = new Member();
        Category category = new Category();
        SubCategory subCategory = new SubCategory();

        when(memberService.findMemberById(memberId)).thenReturn(member);
        when(categoryService.findCategoryById(categoryId)).thenReturn(category);
        when(categoryService.findSubCategoryById(subCategoryId)).thenReturn(subCategory);

        // act
        postService.insertPost(addPostRequestDto, memberId);

        // Assert
        verify(postRepository, times(1)).save(any(Post.class));


    }

    @Test
    @DisplayName("[게시글] 상세 조회")
    void selectPostDetail() {
        // Set up data
        StatesEnum city = StatesEnum.SEOUL;
        Long categoryId = 1L;
        String name = "lim";
        Category category = Category.builder()
                .id(categoryId)
                .build();
        Member member = Member.builder()
                .id(1L)
                .name(name)
                .build();

        List<Post> posts = Arrays.asList(
                Post.builder()
                        .id(1L).title("Test post 1").category(category).states(StatesEnum.SEOUL).contents("Test content1").member(member)
                        .build(),
                Post.builder()
                        .id(2L).title("Test post 2").category(category).states(StatesEnum.BUSAN).contents("Test content2").member(member)
                        .build()
        );

        // Repository return
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(posts.get(0)));
        when(postRepository.findById(2L)).thenReturn(Optional.ofNullable(posts.get(1)));

        // Case 1: postId : 1L 검색
        SelectPostDetailResponseDto result1 = postService.selectPostDetail(1L);
        assertEquals(StatesEnum.SEOUL, result1.getStates());
        assertEquals("Test post 1", result1.getTitle());
        assertEquals("Test content1", result1.getContents());
        assertEquals(name, result1.getMemberName());

        // Case 2: postId : 2L 검색
        SelectPostDetailResponseDto result2 = postService.selectPostDetail(2L);
        assertEquals(StatesEnum.BUSAN, result2.getStates());
        assertEquals("Test post 2", result2.getTitle());
        assertEquals("Test content2", result2.getContents());
        assertEquals(name, result1.getMemberName());
    }

    @Test
    @DisplayName("[게시글] 수정")
    void updatePost() {
        String updateTitle = "테스트 수정 제목";
        String updateContents = "테스트 수정 내용";
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .title(updateTitle)
                .contents(updateContents)
                .states(StatesEnum.BUSAN)
                .build();
        Long postId = 1L;
        Long memberId = 2L;
        Member member = Member.builder().id(memberId).authority(Authority.ROLE_USER).build();
        Post post = Post.builder().id(postId).member(member).build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(memberService.findMemberById(memberId)).thenReturn(member);

        // Act
        postService.updatePost(postId, updatePostRequestDto, memberId);

        // Assert
        assertEquals(updateTitle, postRepository.findById(postId).get().getTitle());
        assertEquals(updateContents, postRepository.findById(postId).get().getContents());

    }
}