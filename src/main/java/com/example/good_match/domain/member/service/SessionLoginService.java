package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.LoginRequestDto;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.repository.MemberRepository;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService{
    private static final String MEMBER_ID = "MEMBER_ID";
    private final HttpSession httpSession;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequest) {
        try {
            Long memberId = isValidMember(loginRequest);
            httpSession.setAttribute(MEMBER_ID, memberId);
        } catch (Exception e) {
            throw new IllegalArgumentException("로그인 실패!");
        }
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

    private Long isValidMember(LoginRequestDto loginRequestDto) {
        Member member = findByEmail(loginRequestDto.getEmail());
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        } else {
            return member.getId();
        }
    }

    private Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
