package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.FindIdRequestDto;
import com.example.good_match.domain.member.dto.request.LoginRequestDto;
import com.example.good_match.domain.member.dto.request.ReissueRequestDto;
import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.dto.response.FindIdResponseDto;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.global.response.ApiResponseDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

    @Transactional
    public ApiResponseDto signUpMember(SignUpRequestDto signUpRequestDto);


    public ApiResponseDto login(LoginRequestDto loginRequestDto);

    public ApiResponseDto reissue(ReissueRequestDto reissueRequestDto);

    public ApiResponseDto<FindIdResponseDto> findId(FindIdRequestDto findIdRequestDto);

    public Member findMemberByJwt(User user);
}
