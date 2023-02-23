package com.example.good_match.domain.board.service;

import com.example.good_match.domain.board.dto.request.AddBoardRequestDto;
import com.example.good_match.domain.board.dto.request.UpdateBoardRequestDto;
import com.example.good_match.global.response.ApiResponseDto;
import org.springframework.security.core.userdetails.User;
public interface BoardService {

    public ApiResponseDto insertBoard(AddBoardRequestDto addBoardRequestDto, User user);

    public ApiResponseDto selectBoardDetail(Long id);

    public ApiResponseDto deleteBoard(Long id, User user);

    public ApiResponseDto updateBoard(Long id, UpdateBoardRequestDto updateBoardRequestDto, User user);
}
