package com.example.good_match.domain.main.controller;

import com.example.good_match.domain.main.dto.response.MainResponseDto;
import com.example.good_match.domain.main.service.MainService;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.util.StatesEnum;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainController {

    private final MainService mainService;

    @ApiOperation(value = "[메인] 메인화면 데이터 조회")
    @GetMapping("")
    public ApiResponseDto<MainResponseDto> selectMainInfo(@RequestParam(name = "states") StatesEnum states) {
        return mainService.selectMainInfo(states);
    }

}
