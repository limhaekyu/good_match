package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.FindIdRequestDto;
import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.dto.response.FindIdResponseDto;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String name = "LIM";
        String phoneNumber = "010-1234-1234";
        String email = "test@gmail.com";
        Member member = Member.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
        FindIdRequestDto findIdRequest = new FindIdRequestDto(name, phoneNumber);
        FindIdResponseDto findIdResponse = new FindIdResponseDto(email);

        when(memberRepository.findByNameAndPhoneNumber(name, phoneNumber)).thenReturn(Optional.ofNullable(member));

        FindIdResponseDto result = memberService.findId(findIdRequest);

        assertEquals(result.getEmail(), findIdResponse.getEmail());
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