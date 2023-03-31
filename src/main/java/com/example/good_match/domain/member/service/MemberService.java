package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.FindIdRequestDto;
import com.example.good_match.domain.member.dto.request.LoginRequestDto;
import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.dto.response.FindIdResponseDto;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.global.response.ApiResponseDto;

public interface MemberService {

    void signUpMember(SignUpRequestDto signUpRequestDto);

    FindIdResponseDto findId(FindIdRequestDto findIdRequestDto);

    void deleteMember(Long memberId);

    Member findMemberById(Long memberId);
}
