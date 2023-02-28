package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.FindIdRequestDto;
import com.example.good_match.domain.member.dto.request.LoginRequestDto;
import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.dto.response.FindIdResponseDto;
import com.example.good_match.domain.member.model.Authority;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.repository.MemberRepository;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    /*
        회원가입
     */

    @Transactional
    public ApiResponseDto signUpMember(SignUpRequestDto signUpRequestDto) {
        try {
            if (memberRepository.existsByPhoneNumber(signUpRequestDto.getPhoneNumber()) && memberRepository.existsByEmail(signUpRequestDto.getEmail())) {
                return ApiResponseDto.of(ResponseStatusCode.REGISTERED.getValue(), "이미 등록된 사용자입니다.");
            } else if(memberRepository.existsByEmail(signUpRequestDto.getEmail())) {
                return ApiResponseDto.of(ResponseStatusCode.REGISTERED.getValue(), "이미 등록된 이메일입니다. ");
            } else if(memberRepository.existsByPhoneNumber(signUpRequestDto.getPhoneNumber())) {
                return ApiResponseDto.of(ResponseStatusCode.REGISTERED.getValue(), "이미 등록된 번호입니다.");
            } else {
                String password = passwordEncoder.encode(signUpRequestDto.getPassword());

                Member member = Member.builder()
                        .email(signUpRequestDto.getEmail())
                        .password(password)
                        .name(signUpRequestDto.getName())
                        .phoneNumber(signUpRequestDto.getPhoneNumber())
                        .gender(signUpRequestDto.getGender())
                        .states(signUpRequestDto.getStates())
                        .authority(Authority.ROLE_USER)
                        .build();

                memberRepository.save(member);

                return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "회원가입에 성공했습니다");
            }
        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "회원가입에 실패했습니다. " + e.getMessage());
        }
    }


    /*
        아이디 찾기
     */

    public ApiResponseDto<FindIdResponseDto> findId(FindIdRequestDto findIdRequestDto) {
        try {
            // 이름 비밀번호 일치여부확인 후 아이디 반환
            Member member = memberRepository.findByNameAndPhoneNumber(findIdRequestDto.getName(), findIdRequestDto.getPhoneNumber())
                    .orElseThrow( () -> new IllegalArgumentException("일치하는 아이디가 없습니다.") );

            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "아이디 찾기에 성공했습니다.",
                    FindIdResponseDto.builder().email(member.getEmail()).build()
            );
        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "아이디 찾기에 실패했습니다. " + e.getMessage());
        }
    }

    /*
        회원 탈퇴
    */
    @Transactional
    public ApiResponseDto deleteMember(Long memberId) {
        try {
            Member member = findMemberById(memberId);
            member.deleteMember();
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "회원 탈퇴를 성공했습니다.");
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.INTERNAL_SERVER_ERROR.getValue(), "회원 탈퇴에 실패했습니다. " + e.getMessage());
        }
    }

    /*
        로그인 회원 검증
    */
    @Override
    public Long isValidMember(LoginRequestDto loginRequestDto) {
        Member member = findByEmail(loginRequestDto.getEmail());
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        } else {
            return member.getId();
        }
    }

    @Override
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow( () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
    private Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
