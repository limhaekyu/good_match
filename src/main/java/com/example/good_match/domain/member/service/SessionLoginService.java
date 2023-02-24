package com.example.good_match.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService{

    private static final String MEMBER_ID = "MEMBER_ID";
    private final HttpSession httpSession;

    @Override
    public void login(Long memberId) {
        httpSession.setAttribute(MEMBER_ID, memberId);
    }

    @Override
    public void logout() {
    httpSession.removeAttribute(MEMBER_ID);
    }

    @Override
    public String getCurrentMemberId() {
        return (String)httpSession.getAttribute(MEMBER_ID);
    }
}
