package com.example.good_match.domain.member.service;

import com.example.good_match.domain.member.dto.request.FindIdRequestDto;
import com.example.good_match.domain.member.dto.request.LoginRequestDto;
import com.example.good_match.domain.member.dto.request.ReissueRequestDto;
import com.example.good_match.domain.member.dto.request.SignUpRequestDto;
import com.example.good_match.domain.member.dto.response.FindIdResponseDto;
import com.example.good_match.domain.member.model.Authority;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.repository.MemberRepository;
import com.example.good_match.domain.member.repository.RefreshTokenRedisRepository;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import com.example.good_match.global.security.jwt.TokenProvider;
import com.example.good_match.global.security.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final RedisTemplate<String, String> redisTemplate;
    // 회원가입

    @Transactional
    public ApiResponseDto signUpMember(SignUpRequestDto signUpRequestDto) {
        try {
            String password = passwordEncoder.encode(signUpRequestDto.getPassword());

            Member member = Member.builder()
                    .email(signUpRequestDto.getEmail())
                    .password(password)
                    .name(signUpRequestDto.getName())
                    .phoneNumber(signUpRequestDto.getPhoneNumber())
                    .gender(signUpRequestDto.getGender())
                    .authority(Authority.ROLE_USER)
                    .build();

            memberRepository.save(member);

            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "회원가입에 성공했습니다");

        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "회원가입에 실패했습니다. " + e.getMessage());
        }
    }

    // 로그인
    @Transactional
    public ApiResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            // Login ID/PW 기반으로 AuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

            // 검증 (사용자 비밀번호 체크)
            // authenticate 메서드 실행 -> CustomUserDetailService에서 만든 loadUserByUsername 메서드 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 인증 정보를 기반 JWT 토큰 생성 문제
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

            // redis에 refreshToken 저장하는것 필요
            redisTemplate.opsForValue().set(
                    authentication.getName(),
                    tokenDto.getRefreshToken(),
                    tokenDto.getRefreshTokenExpiresIn(),
                    TimeUnit.MILLISECONDS
            );

            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "로그인 성공", tokenDto);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "로그인 실패 " + e.getMessage());
        }
    }


    @Transactional
    public ApiResponseDto reissue(ReissueRequestDto reissueRequestDto) {
        try {
            // refresh token 검증
            if (!tokenProvider.validateToken(reissueRequestDto.getRefreshToken())) {
                throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
            }

            // AccessToken에서 MemberId 가져오기
            Authentication authentication = tokenProvider.getAuthentication(reissueRequestDto.getAccessToken());

            // 저장소에서 MemberId 기반으로 Refresh token 값 가져옴
            String refreshToken = redisTemplate.opsForValue().get(authentication.getName());

            // Refresh token이 일치하는지 검사
            if (!refreshToken.equals(reissueRequestDto.getRefreshToken())) {
                throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
            }

            // 새로운 토큰 생성
            TokenDto newToken = tokenProvider.generateTokenDto(authentication);

            // 저장소 정보 업데이트
            redisTemplate.opsForValue().set(
                    authentication.getName(),
                    newToken.getRefreshToken(),
                    newToken.getRefreshTokenExpiresIn(),
                    TimeUnit.MILLISECONDS
            );

            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "Access Token 재발급 완료", newToken);
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "토큰 재발급에 실패했습니다. " + e.getMessage());
        }
    }

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
}
