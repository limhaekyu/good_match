package com.example.good_match.domain.member.repository;

import com.example.good_match.domain.member.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByMemberId(Long MemberId);
}
