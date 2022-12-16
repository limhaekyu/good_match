package com.example.good_match.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReissueRequestDto {
    private String accessToken;
    private String refreshToken;
}
