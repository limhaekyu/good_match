package com.example.good_match.domain.member.service;

public interface LoginService {
    void login(Long memberId);
    void logout();
    String getCurrentMemberId();
}
