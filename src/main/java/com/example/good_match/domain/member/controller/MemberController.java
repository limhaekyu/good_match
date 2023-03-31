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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @ApiOperation(value = "[회원] 회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.signUpMember(signUpRequestDto);
    }

    @ApiOperation(value = "[회원] 로그인")
    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto) {
        loginService.login(loginRequestDto);
    }

    @ApiOperation(value = "[회원] 로그아웃")
    @LoginRequired
    @PostMapping("/logout")
    public void logout() {
        loginService.logout();
    }

    @ApiOperation(value = "[회원] 아이디 찾기")
    @PostMapping("/find-id")
    public ResponseEntity<FindIdResponseDto> findId(@RequestBody FindIdRequestDto findIdRequestDto){
        return ResponseEntity.ok(memberService.findId(findIdRequestDto));
    }

    @ApiOperation(value = "[회원] 회원 탈퇴")
    @LoginRequired
    @DeleteMapping("/member")
    public void deleteMember(@CurrentMemberId Long memberId) {
        memberService.deleteMember(memberId);
    }
}
