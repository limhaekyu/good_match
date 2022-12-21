package com.example.good_match.domain.game.controller;

import com.example.good_match.domain.game.dto.request.AddGameRequestDto;
import com.example.good_match.domain.game.service.GameService;
import com.example.good_match.global.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GameController {
    // 게시글 등록, 게시글 조회 상세 ,게시글 삭제, 게시글 수정

    private final GameService gameService;

    @ApiOperation(value = "[매칭] 게임 게시글 등록")
    @PostMapping("/game")
    public ApiResponseDto addGame(@AuthenticationPrincipal User user, @RequestBody AddGameRequestDto addGameRequestDto) {
        return gameService.addGame(addGameRequestDto, user);
    }

    @ApiOperation(value = "[매칭] 게임 게시글 상세조회")
    @GetMapping("/game/{id}")
    public ApiResponseDto selectGameDetail(@PathVariable Long id){
        return gameService.selectGameDetail(id);
    }


}
