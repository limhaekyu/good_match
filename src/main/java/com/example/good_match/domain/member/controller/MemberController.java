package com.example.good_match.domain.member.controller;

import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.global.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ApiResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return memberService.signUpMember(signUpRequestDto);
    }
}
