package com.example.good_match.global.interceptor;

import com.example.good_match.domain.member.model.Authority;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.domain.member.service.LoginService;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.global.annotation.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.login.LoginException;
import javax.security.auth.message.AuthException;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final MemberService memberService;
    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(Auth.class)) {
            Long memberId = loginService.getCurrentMemberId();
            Member member = memberService.findMemberById(memberId);

            if (!member.getAuthority().equals(Authority.ROLE_ADMIN)) {
                throw new AuthException("권한이 없습니다.");
            }
        }
        return true;
    }
}
