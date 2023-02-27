package com.example.good_match.global.interceptor;

import com.example.good_match.domain.member.service.LoginService;
import com.example.good_match.global.annotation.LoginRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(LoginRequired.class)) {
            Long memberId = loginService.getCurrentMemberId();

            if (memberId == null) {
                throw new AuthenticationException("로그인이 필요합니다.");
            }
        }
        return true;
    }
}
