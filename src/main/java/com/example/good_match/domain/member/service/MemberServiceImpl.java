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
import com.nimbusds.oauth2.sdk.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
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
    public void signUpMember(SignUpRequestDto signUpRequestDto) {
        try {
            if (memberRepository.existsByPhoneNumber(signUpRequestDto.getPhoneNumber()) && memberRepository.existsByEmail(signUpRequestDto.getEmail())) {
                throw new DuplicateKeyException("이미 등록된 번호와 이메일입니다.");
            } else if(memberRepository.existsByEmail(signUpRequestDto.getEmail())) {
                throw new DuplicateKeyException("이미 등록된 이메일입니다.");
            } else if(memberRepository.existsByPhoneNumber(signUpRequestDto.getPhoneNumber())) {
                throw new DuplicateKeyException("이미 등록된 전화번호입니다.");
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
            }
        } catch (Exception e){
            throw new IllegalArgumentException("회원가입 실패! " + e.getMessage());
        }
    }


    /*
        아이디 찾기
     */

    public FindIdResponseDto findId(FindIdRequestDto findIdRequestDto) {
        try {
            // 이름 비밀번호 일치여부확인 후 아이디 반환
            Member member = memberRepository.findByNameAndPhoneNumber(findIdRequestDto.getName(), findIdRequestDto.getPhoneNumber())
                    .orElseThrow( () -> new UsernameNotFoundException("일치하는 아이디가 없습니다.") );

            return FindIdResponseDto.builder().email(member.getEmail()).build();
        } catch (Exception e){
            throw new IllegalArgumentException("아이디 찾기 실패! " + e.getMessage());
        }
    }

    /*
        회원 탈퇴
    */
    @Transactional
    public void deleteMember(Long memberId) {
        try {
            Member member = findMemberById(memberId);
            member.deleteMember();
        } catch (Exception e) {
            throw new IllegalArgumentException("회원탈퇴 실패! " + e.getMessage());
        }
    }

    @Override
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow( () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

}
