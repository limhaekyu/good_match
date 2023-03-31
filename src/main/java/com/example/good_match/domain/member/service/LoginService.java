package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.LoginRequestDto;
import com.example.good_match.global.response.ApiResponseDto;

public interface LoginService {
    void login(LoginRequestDto loginRequest);
    ApiResponseDto logout();
    Long getCurrentMemberId();
}
