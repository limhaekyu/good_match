package com.example.good_match.domain.member.repository;

import com.example.good_match.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String username);

    Optional<Member> findByNameAndPhoneNumber(String name, String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}
