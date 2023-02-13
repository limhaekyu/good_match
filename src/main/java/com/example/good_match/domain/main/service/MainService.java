package com.example.good_match.domain.main.service;

import com.example.good_match.domain.main.dto.response.MainResponseDto;
import com.example.good_match.global.response.ApiResponseDto;

public interface MainService {

    public ApiResponseDto<MainResponseDto> showMainInfo();
}
