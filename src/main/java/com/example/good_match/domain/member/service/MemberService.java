package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.FindIdRequestDto;
import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.dto.response.FindIdResponseDto;
import com.example.good_match.global.response.ApiResponseDto;
import org.springframework.security.core.userdetails.User;

public interface MemberService {

    public ApiResponseDto signUpMember(SignUpRequestDto signUpRequestDto);

    public ApiResponseDto<FindIdResponseDto> findId(FindIdRequestDto findIdRequestDto);

    ApiResponseDto deleteMember(User user);
}
