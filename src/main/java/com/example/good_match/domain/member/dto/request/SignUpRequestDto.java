package com.example.good_match.domain.member.dto.request;

import com.example.good_match.domain.member.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SignUpRequestDto {
    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private Gender gender;
}
