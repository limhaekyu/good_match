package com.example.good_match.domain.board.controller;

import com.example.good_match.domain.board.dto.request.AddBoardRequestDto;
import com.example.good_match.domain.board.dto.request.UpdateBoardRequestDto;
import com.example.good_match.domain.board.service.BoardService;
import com.example.good_match.global.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    //게시글 수정

    private final BoardService boardService;

    @ApiOperation(value = "[매칭] 게임 게시글 등록")
    @PostMapping("")
    public ApiResponseDto addGame(@AuthenticationPrincipal User user, @RequestBody AddBoardRequestDto addBoardRequestDto) {
        return boardService.addGame(addBoardRequestDto, user);
    }

    @ApiOperation(value = "[매칭] 게임 게시글 상세조회")
    @GetMapping("/{id}")
    public ApiResponseDto selectGameDetail(@PathVariable Long id){
        return boardService.selectGameDetail(id);
    }

    @ApiOperation(value = "[매칭] 게임 게시글 삭제")
    @DeleteMapping("/{id}")
    public ApiResponseDto deleteGame(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return boardService.deleteGame(id, user);
    }

    // 게시글 수정
    @ApiOperation(value = "[매칭] 게임 게시글 수정")
    @PutMapping("/{id}")
    public ApiResponseDto updateGame(@PathVariable Long id, @RequestBody UpdateBoardRequestDto updateBoardRequestDto, @AuthenticationPrincipal User user) {
        return boardService.updateGame(id, updateBoardRequestDto, user);
    }

}
