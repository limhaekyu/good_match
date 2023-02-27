package com.example.good_match.domain.member.controller;

import com.example.good_match.domain.member.dto.request.FindIdRequestDto;
import com.example.good_match.domain.member.dto.request.LoginRequestDto;
import com.example.good_match.domain.member.dto.request.ReissueRequestDto;
import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.dto.response.FindIdResponseDto;
import com.example.good_match.domain.member.service.LoginService;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.global.annotation.CurrentMemberId;
import com.example.good_match.global.annotation.LoginRequired;
import com.example.good_match.global.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @ApiOperation(value = "[회원] 회원가입")
    @PostMapping("/sign-up")
    public ApiResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return memberService.signUpMember(signUpRequestDto);
    }

    @ApiOperation(value = "[회원] 로그인")
    @PostMapping("/login")
    public ApiResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        Long memberId = memberService.isValidMember(loginRequestDto);
        return loginService.login(memberId);
    }

    @ApiOperation(value = "[회원] 로그아웃")
    @PostMapping("/logout")
    public ApiResponseDto logout() {
        return loginService.logout();
    }

    @ApiOperation(value = "[회원] 아이디 찾기")
    @PostMapping("/find-id")
    public ApiResponseDto<FindIdResponseDto> findId(@RequestBody FindIdRequestDto findIdRequestDto){
        return memberService.findId(findIdRequestDto);
    }

    @ApiOperation(value = "[회원] 회원 탈퇴")
    @LoginRequired
    @DeleteMapping("/member")
    public ApiResponseDto deleteMember(@CurrentMemberId Long memberId) {
        return memberService.deleteMember(memberId);
    }
}
