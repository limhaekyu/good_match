package com.example.good_match.domain.board.service;

import com.example.good_match.domain.board.domain.Board;
import com.example.good_match.domain.board.domain.BoardStatus;
import com.example.good_match.domain.board.dto.request.AddBoardRequestDto;
import com.example.good_match.domain.board.dto.request.UpdateBoardRequestDto;
import com.example.good_match.domain.board.dto.response.SelectBoardDetailResponseDto;
import com.example.good_match.domain.board.repository.BoardRepository;
import com.example.good_match.domain.category.repository.CategoryRepository;
import com.example.good_match.domain.category.repository.SubCategoryRepository;
import com.example.good_match.domain.member.service.MemberService;
import com.example.good_match.global.response.ApiResponseDto;
import com.example.good_match.global.response.ResponseStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    /*
        [매칭] 게임 게시글 등록
     */
    @Transactional
    public ApiResponseDto insertBoard(AddBoardRequestDto addBoardRequestDto, User user) {
        try{
            boardRepository.save(Board.builder()
                    .title(addBoardRequestDto.getTitle())
                    .contents(addBoardRequestDto.getContents())
                    .states(addBoardRequestDto.getStates())
                    .boardStatus(BoardStatus.recruiting)
                    .member(memberService.findMemberByJwt(user))
                    .category(categoryRepository.findById(addBoardRequestDto.getCategoryId()).orElseThrow(()->new IllegalArgumentException("없는 카테고리입니다.")))
                    .subCategory(subCategoryRepository.findById(addBoardRequestDto.getSubCategoryId()).orElseThrow(()->new IllegalArgumentException("없는 서브 카테고리입니다.")))
                    .build());
            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글 등록을 성공했습니다. ");
        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글 등록에 실패했습니다. " + e.getMessage());
        }
    }


    /*
        [매칭] 게임 게시글 상세 조회
     */
    @Transactional
    public ApiResponseDto selectBoardDetail(Long id) {
        try {
            Board board = findBoardById(id);

            return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글 상세 조회에 성공했습니다.",
                    SelectBoardDetailResponseDto.builder()
                            .title(board.getTitle())
                            .contents(board.getContents())
                            .states(board.getStates())
                            .boardStatus(board.getBoardStatus())
                            .updatedAt(board.getUpdatedAt())
                            .memberId(board.getMember().getId())
                            .memberName(board.getMember().getName())
                            .build()
            );
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글 상세 조회에 실패했습니다. " + e.getMessage());
        }
    }


    /*
        [매칭] 게임 게시글 삭제
     */

    @Transactional
    public ApiResponseDto deleteBoard(Long id, User user) {
        try {
            Board board = findBoardById(id);
            if (memberService.findMemberByJwt(user) == board.getMember() || user.getAuthorities().equals("ROLE_ADMIN")) {
                boardRepository.delete(board);
                return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글을 삭제했습니다.");
            } else {
                return ApiResponseDto.of(ResponseStatusCode.UNAUTHORIZED.getValue(), "해당 사용자의 게임 메칭 게시글이 아닙니다.");
            }
        } catch (Exception e) {
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글을 삭제했습니다.. " + e.getMessage());
        }
    }


    /*
        [매칭] 게임 게시글 찾기 (with. index id)
     */

    private Board findBoardById(Long id) {
        return boardRepository.findById(id).orElseThrow( ()-> new IllegalArgumentException("없는 게임 매칭 게시글입니다.") );
    }

    /*
        [매칭] 게임 게시글 수정
     */

    @Transactional
    public ApiResponseDto updateBoard(Long id, UpdateBoardRequestDto updateBoardRequestDto, User user) {
        try {
            Board board = findBoardById(id);

            if (memberService.findMemberByJwt(user) == board.getMember() || user.getAuthorities().equals("ROLE_ADMIN")) {

                board.updateBoard(updateBoardRequestDto.getTitle(), updateBoardRequestDto.getContents(), updateBoardRequestDto.getStates());
                boardRepository.save(board);

                return ApiResponseDto.of(ResponseStatusCode.SUCCESS.getValue(), "게임 매칭 게시글 수정을 완료했습니다.");
            } else {
                return ApiResponseDto.of(ResponseStatusCode.UNAUTHORIZED.getValue(), "수정 권한이 없습니다. ");
            }

        } catch (Exception e){
            return ApiResponseDto.of(ResponseStatusCode.FAIL.getValue(), "게임 매칭 게시글 수정에 실패했습니다. " + e.getMessage());
        }
    }

}
