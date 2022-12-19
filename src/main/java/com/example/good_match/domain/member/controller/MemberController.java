package com.example.good_match.domain.member.controller;

import com.example.good_match.domain.member.dto.request.FindIdRequestDto;
import com.example.good_match.domain.member.dto.request.LoginRequestDto;
import com.example.good_match.domain.member.dto.request.ReissueRequestDto;
import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.dto.response.FindIdResponseDto;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.global.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    public ApiResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return memberService.signUpMember(signUpRequestDto);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ApiResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return memberService.login(loginRequestDto);
    }

    @ApiOperation(value = "엑세스 토큰 재발급")
    @PostMapping("/reissue")
    public ApiResponseDto reissue(@RequestBody ReissueRequestDto reissueRequestDto) {
        return memberService.reissue(reissueRequestDto);
    }

    @ApiOperation(value = "아이디 찾기")
    @PostMapping("/find-id")
    public ApiResponseDto<FindIdResponseDto> findId(@RequestBody FindIdRequestDto findIdRequestDto){
        return memberService.findId(findIdRequestDto);
    }
}
