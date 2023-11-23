package com.example.kh.testProject.service;

import com.example.kh.testProject.dto.BoardDto;
import com.example.kh.testProject.entity.Board;
import com.example.kh.testProject.entity.Category;
import com.example.kh.testProject.entity.Member;
import com.example.kh.testProject.repository.BoardRepository;
import com.example.kh.testProject.repository.CategoryRepository;
import com.example.kh.testProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    // 게시글 등록
    public boolean saveBoard(BoardDto boardDto) {
        try {
            Board board = new Board();
            Member member = memberRepository.findByEmail(boardDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 없습니다.")
            );
            Category category = categoryRepository.findById(boardDto.getCategoryId()).orElseThrow(
                    () -> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
            );
            board.setTitle(boardDto.getTitle());
            board.setContent(boardDto.getContent());
            board.setCategory(category);
            board.setImgPath(boardDto.getImg());
            board.setMember(member);
            boardRepository.save(board);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 게시글 전체 조회
    public List<BoardDto> getBoardList() {
        // 엔티티의 모든 객체들을 리스트에 넣어서 각 객체들을 순회하며 조회하는 메서드
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boardList) {
            boardDtoList.add(convertEntityToDto(board));
        }
        return boardDtoList;
    }

    // 게시글 상세 조회
    public BoardDto getBoardDetail(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );
        return convertEntityToDto(board);
    }

    // 게시글 수정
    public boolean modifyBoard(Long id, BoardDto boardDto) {
        try {
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
            );
            // 매개변수로 전달받은 dto로 부터 정보를 board 엔티티에 저장
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setImgPath(boardDto.getImg());
        boardRepository.save(board);
        return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 게시글 삭제
    public boolean deleteBoard(Long id) {
        try {
            if(memberRepository.findById(id) != null) {
                memberRepository.deleteById(id);
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 게시글 찾기
    public List<BoardDto> searchBoard(String keyword) {
        List<Board> boardList = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boardList) {
            boardDtoList.add(convertEntityToDto(board));
        }
        return boardDtoList;
    }

    // 게시글 페이징
    public List<BoardDto> getBoardList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Board> boards = boardRepository.findAll(pageable).getContent();
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boards) {
            boardDtos.add(convertEntityToDto(board));
        }
        return boardDtos;
    }

    // 페이지 수 조회
    public int getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable).getTotalPages();
    }

    // board를 boardDto로 변환
    private BoardDto convertEntityToDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setTitle(board.getTitle());
        boardDto.setCategoryId(board.getCategory().getCategoryId());
        boardDto.setContent(board.getContent());
        boardDto.setImg(board.getImgPath());
        boardDto.setEmail(board.getMember().getEmail());
        boardDto.setRegDate(board.getRegDate());
        return boardDto;
    }

}
