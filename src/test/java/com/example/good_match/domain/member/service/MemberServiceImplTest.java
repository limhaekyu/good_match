package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.model.Authority;
import com.example.good_match.domain.member.model.Gender;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.repository.MemberRepository;
import com.example.good_match.global.util.StatesEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberServiceImpl memberService;


    @Test
    void signUpMember() {
        SignUpRequestDto signUpRequest = SignUpRequestDto.builder()
                .email("test@gmail.com")
                .password("123123")
                .name("LIM")
                .phoneNumber("010-1234-1234")
                .gender(Gender.MAN)
                .states(StatesEnum.SEOUL)
                .build();
        String exPassword = passwordEncoder.encode(signUpRequest.getPassword());

        when(memberRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())).thenReturn(false);
        when(memberRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn(exPassword);

        memberService.signUpMember(signUpRequest);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void findId() {
    }

    @Test
    void deleteMember() {
    }

    @Test
    void isValidMember() {
    }

    @Test
    void findMemberById() {
    }
}