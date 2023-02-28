package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.LoginRequestDto;
import com.example.good_match.global.response.ApiResponseDto;

public interface LoginService {
    ApiResponseDto login(Long memberId);
    ApiResponseDto logout();
    Long getCurrentMemberId();
}
