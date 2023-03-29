package com.example.good_match.domain.post.repository;

import com.example.good_match.domain.category.model.Category;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.global.exception.NullSearchKeywordException;
import com.example.good_match.global.util.StatesEnum;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.good_match.domain.post.domain.QPost.post;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    public PostRepositorySupport(JPAQueryFactory queryFactory,
                                 CategoryRepository categoryRepository,
                                 PostRepository postRepository) {
        super(Post.class);
        this.queryFactory = queryFactory;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public List<Post> searchByKeyword(StatesEnum states, Long categoryId, String keyword) {
        return queryFactory
                .selectFrom(post)
                .where(eqStates(states),
                        eqCategory(categoryId),
                        eqKeyword(keyword))
                .fetch();
    }

    private BooleanExpression eqStates(StatesEnum states) {
        if (StatesEnum.NATIONWIDE.equals(states)) {
            return null;
        }
        return post.states.eq(states);
    }

    private BooleanExpression eqCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return post.category.eq(categoryRepository.findById(categoryId).orElseThrow(()->new IllegalArgumentException("해당 카테고리는 없는 카테고리입니다.")));
    }

    private BooleanExpression eqKeyword(String keyword) {
        if (keyword == null) {
            throw new NullSearchKeywordException();
        }
        return post.title.contains(keyword);
    }
}
