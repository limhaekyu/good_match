package com.example.good_match.domain.game.service;

import com.example.good_match.domain.game.domain.Game;
import com.example.good_match.domain.game.domain.GameStatus;
import com.example.good_match.domain.game.dto.request.AddGameRequestDto;
import com.example.good_match.domain.game.dto.response.SelectGameDetailResponseDto;
import com.example.good_match.domain.game.repository.GameRepository;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final MemberService memberService;

    @Transactional
    public ApiResponseDto addGame(AddGameRequestDto addGameRequestDto, User user) {
        try{
            gameRepository.save(Game.builder()
                    .title(addGameRequestDto.getTitle())
                    .contents(addGameRequestDto.getContents())
                    .states(addGameRequestDto.getStates())
                    .gameStatus(GameStatus.reservation_wait)
                    .member(memberService.findMemberByJwt(user))
                    .build());
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글 등록을 성공했습니다. ");
        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글 등록에 실패했습니다. " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponseDto selectGameDetail(Long id) {
        try {
            Game game = findGameById(id);

            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글 상세 조회에 성공했습니다.",
                    SelectGameDetailResponseDto.builder()
                            .title(game.getTitle())
                            .contents(game.getContents())
                            .states(game.getStates())
                            .gameStatus(game.getGameStatus())
                            .updatedAt(game.getUpdatedAt())
                            .memberId(game.getMember().getId())
                            .memberName(game.getMember().getName())
                            .build()
            );
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글 상세 조회에 실패했습니다. " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponseDto deleteGame(Long id, User user) {
        try {
            Game game = findGameById(id);
            if (memberService.findMemberByJwt(user).equals(game.getMember())) {
                gameRepository.delete(game);
                return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글을 삭제했습니다.");
            } else {
                return ApiResponseDto.of(ResponseStatusCode.UNAUTHORIZED.getValue(), "해당 사용자의 게임 메칭 게시글이 아닙니다.");
            }
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글을 삭제했습니다.. " + e.getMessage());
        }
    }

    private Game findGameById(Long id) {
        return gameRepository.findById(id).orElseThrow( ()-> new IllegalArgumentException("없는 게임 매칭 게시글입니다.") );
    }
}
