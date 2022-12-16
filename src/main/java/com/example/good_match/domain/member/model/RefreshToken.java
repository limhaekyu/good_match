package com.example.good_match.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id
    private String memberId;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public void updateValue(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
