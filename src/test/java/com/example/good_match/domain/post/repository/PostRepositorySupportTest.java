package com.example.good_match.domain.post.repository;

import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.model.SubCategory;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.global.util.StatesEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostRepositorySupportTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostRepositorySupport postRepositorySupport;

    @AfterEach
    void AfterSet() {
        postRepository.deleteAllInBatch();
    }


    @Test
    @DisplayName("[검색쿼리 테스트] 지역별,카테고리별 키워드")
    void findByStatesAndCategoryAndTitleContaining() {
        // given
        postRepository.save(Post.builder()
                .title("서울 크루!")
                .contents("풋살")
                .states(StatesEnum.SEOUL)
                .member(Member.builder().id(1L).build())
                .category(Category.builder().id(1L).build())
                .subCategory(SubCategory.builder().id(1L).build())
                .build());
        postRepository.save(Post.builder()
                .title("서울 크루.")
                .contents("러닝")
                .states(StatesEnum.BUSAN)
                .member(Member.builder().id(1L).build())
                .category(Category.builder().id(2L).build())
                .subCategory(SubCategory.builder().id(3L).build())
                .build());
        postRepository.save(Post.builder()
                .title("부산 크루!")
                .contents("풋살")
                .states(StatesEnum.BUSAN)
                .member(Member.builder().id(1L).build())
                .category(Category.builder().id(1L).build())
                .subCategory(SubCategory.builder().id(1L).build())
                .build());
        postRepository.save(Post.builder()
                .title("부산 크루.")
                .contents("러닝")
                .states(StatesEnum.BUSAN)
                .member(Member.builder().id(1L).build())
                .category(Category.builder().id(2L).build())
                .subCategory(SubCategory.builder().id(3L).build())
                .build());
        List<Post> postsByKeyword;
        // when
        postsByKeyword = postRepositorySupport.searchByKeyword(StatesEnum.BUSAN, 1L, "크루" );
        // then
        assertThat(postsByKeyword.size()).isEqualTo(1);
        assertThat(postsByKeyword.get(postsByKeyword.size()-1).getTitle()).isEqualTo("부산 크루!");
    }
}