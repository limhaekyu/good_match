package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.model.ResponseStatusCode;
import com.example.good_match.domain.member.repository.MemberRepository;
import com.example.good_match.global.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입

    @Transactional
    public ApiResponseDto signUpember(SignUpRequestDto signUpRequestDto) {
        try {

            String password = passwordEncoder.encode(signUpRequestDto.getPassword());

            Member member = Member.builder()
                    .username(signUpRequestDto.getId())
                    .password(password)
                    .name(signUpRequestDto.getName())
                    .phoneNumber(signUpRequestDto.getPhoneNumber())
                    .email(signUpRequestDto.getEmail())
                    .build();
            memberRepository.save(member);

            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "회원가입에 성공했습니다");

        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "회원가입에 실패했습니다. " + e.getMessage());
        }
    }
}
