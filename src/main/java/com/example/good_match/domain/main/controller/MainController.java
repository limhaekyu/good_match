package com.example.good_match.domain.main.controller;

import com.example.good_match.domain.main.dto.response.MainResponseDto;
import com.example.good_match.domain.main.service.MainService;
import com.example.good_match.global.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {

    private final MainService mainService;

    @GetMapping("")
    public ApiResponseDto<MainResponseDto> showMainInfo() {
        return mainService.showMainInfo();
    }
}
