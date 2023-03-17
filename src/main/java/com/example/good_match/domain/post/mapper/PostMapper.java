package com.example.good_match.domain.post.mapper;

import com.example.good_match.domain.main.dto.response.PostResponseDto;
import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.global.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper extends GenericMapper<PostResponseDto, Post> {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
}