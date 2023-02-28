package com.example.good_match.domain.member.service;

import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService{
    private static final String MEMBER_ID = "MEMBER_ID";
    private final HttpSession httpSession;

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto login(Long memberId) {
        httpSession.setAttribute(MEMBER_ID, memberId);
        return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "로그인 성공!");
    }

    @Override
    public ApiResponseDto logout() {
        httpSession.removeAttribute(MEMBER_ID);
        return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "로그아웃 완료!");
    }

    @Override
    public Long getCurrentMemberId() {
        return (Long) httpSession.getAttribute(MEMBER_ID);
    }
}
