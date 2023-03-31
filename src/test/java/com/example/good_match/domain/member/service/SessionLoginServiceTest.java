package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.LoginRequestDto;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionLoginServiceTest {

    @Mock
    private HttpSession httpSession;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private SessionLoginService loginService;

    private static final String MEMBER_ID = "MEMBER_ID";


    @Test
    @DisplayName("[회원] 로그인 테스트")
    void login() {
        String email = "lim@gmail.com";
        String password = "123123";
        LoginRequestDto loginRequest = new LoginRequestDto(email, password);
        Member member = Member.builder()
                .id(1L)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        when(memberRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())).thenReturn(true);

        loginService.login(loginRequest);

        verify(httpSession).setAttribute(MEMBER_ID, 1L);
    }

    @Test
    @DisplayName("[회원] 로그아웃 테스트")
    void logout() {
    }

}