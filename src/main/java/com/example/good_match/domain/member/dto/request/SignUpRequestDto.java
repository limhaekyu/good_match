package com.example.good_match.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SignUpRequestDto {
    private String id;

    private String password;

    private String name;

    private String phoneNumber;

    private String email;
}
