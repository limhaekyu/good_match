package com.example.good_match.domain.post.repository;

import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.main.dto.response.PostResponseDto;
import com.example.good_match.domain.member.repository.MemberRepository;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.global.util.StatesEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
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

        List<Post> posts = postRepository.findCurrentPosts(Pageable.ofSize(7));
        // then
        assertThat(posts.size()).isEqualTo(7);
    }
}