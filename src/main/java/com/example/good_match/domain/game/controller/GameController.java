package com.example.good_match.domain.game.controller;

import com.example.good_match.domain.game.dto.request.AddGameRequestDto;
import com.example.good_match.domain.game.dto.request.UpdateGameRequestDto;
import com.example.good_match.domain.game.service.GameService;
import com.example.good_match.global.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GameController {
    //게시글 수정

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

    @ApiOperation(value = "[매칭] 게임 게시글 삭제")
    @DeleteMapping("/game/{id}")
    public ApiResponseDto deleteGame(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return gameService.deleteGame(id, user);
    }

    // 게시글 수정
    @ApiOperation(value = "[매칭] 게임 게시글 수정")
    @PutMapping("/game/{id}")
    public ApiResponseDto updateGame(@PathVariable Long id, @RequestBody UpdateGameRequestDto updateGameRequestDto, @AuthenticationPrincipal User user) {
        return gameService.updateGame(id, updateGameRequestDto, user);
    }

}
