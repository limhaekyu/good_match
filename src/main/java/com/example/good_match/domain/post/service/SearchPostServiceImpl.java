package com.example.good_match.domain.post.service;

import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.post.dto.response.SearchPostResponseDto;
import com.example.good_match.domain.post.repository.PostRepositorySupport;
import com.example.good_match.global.exception.NullSearchKeywordException;
import com.example.good_match.global.util.StatesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchPostServiceImpl implements SearchPostService {

    private final PostRepositorySupport postRepositorySupport;


    @Override
    @Transactional(readOnly = true)
    public List<SearchPostResponseDto> searchPosts(StatesEnum city, Long categoryId, String keyword) throws NullSearchKeywordException {

        List<Post> posts;
        posts = postRepositorySupport.searchByKeyword(city, categoryId, keyword);

        return posts.stream().map(SearchPostResponseDto::new).collect(Collectors.toList());
    }

}
