package com.example.good_match.domain.post.repository;

import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.repository.SubCategoryRepository;
import com.example.good_match.domain.main.dto.response.PostResponseDto;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.repository.MemberRepository;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.global.util.StatesEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;


    @Test
    @DisplayName("최근 게시글 7개 조회 테스트")
    void findCurrentPosts() {
        Category category = categoryRepository.getById(1L);
        // given
        Post post1 = Post.builder()
                .title("용병구해요")
                .contents("풋살용병!")
                .member(memberRepository.findById(1L).orElseThrow())
                .states(StatesEnum.SEOUL)
                .category(category)
                .subCategory(category.getSubCategories().get(0))
                .build();
        Post post2 = Post.builder()
                .title("러닝메이트 구합니다.")
                .contents("부산러닝!")
                .member(memberRepository.findById(1L).orElseThrow())
                .states(StatesEnum.SEOUL)
                .category(category)
                .subCategory(category.getSubCategories().get(0))
                .build();
        for (int i=-0; i < 7; i++) {
            postRepository.save(post1);
            postRepository.save(post2);
        }
        // when

        List<Post> posts = postRepository.findRecentPosts(Pageable.ofSize(7));
        // then
        assertThat(posts.size()).isEqualTo(7);
    }

    @Test
    @DisplayName("카테고리별 게시글 10개씩 조회 테스트")
    void findAllBySubCategory() {
        // given
        Member member = Member.builder()
                .name("member1")
                .email("member1@gmail.com")
                .states(StatesEnum.SEOUL)
                .phoneNumber("010-1010-1010")
                .build();
        Category category = Category.builder()
                .title("cate1")
                .build();
        SubCategory subCategory = SubCategory.builder()
                .subCategoryTitle("sub1")
                .category(category)
                .build();
        memberRepository.save(member);
        categoryRepository.save(category);
        subCategoryRepository.save(subCategory);

        for(int i=0; i<20; i++) {
            postRepository.save(Post.builder()
                            .title("post"+i)
                            .contents("contents"+i)
                            .member(member)
                            .category(category)
                            .subCategory(subCategory)
                    .build());
        }

        // when
        Page<Post> postsPage = postRepository.findAllBySubCategory(subCategory, PageRequest.of(0, 10, Sort.by("id").descending()));
        // then
        assertThat(postsPage.getSize()).isEqualTo(10);
        assertThat(postsPage.getContent().get(0).getTitle()).isEqualTo("post19");
        assertThat(postsPage.getContent().get(9).getTitle()).isEqualTo("post10");
    }
}