package com.example.kh.testProject.controller;

import com.example.kh.testProject.dto.CommentDto;
import com.example.kh.testProject.service.CategoryService;
import com.example.kh.testProject.service.CommentService;
import com.example.kh.testProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kh.testProject.utils.Common.CORS_ORIGIN;

@Slf4j
@CrossOrigin(origins = CORS_ORIGIN)
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;
    private final CategoryService categoryService;

    // 회원 등록
    @PostMapping("/new")
    public ResponseEntity<Boolean> commentRegister(@RequestBody CommentDto commentDto) {
        boolean isTrue = commentService.commentRegister(commentDto);
        return ResponseEntity.ok(isTrue);
    }

    // 댓글 수정
    @PostMapping("/modify")
    public ResponseEntity<Boolean> commentModify(@RequestBody CommentDto commentDto) {
        boolean isTrue = commentService.commentModify(commentDto);
        return ResponseEntity.ok(isTrue);
    }

    // 댓글 삭제
    @PostMapping("/delete/{commentId}")
    public ResponseEntity<Boolean> commentDelete(@PathVariable Long commentid) {
        boolean isTrue = commentService.commentDelete(commentid);
        return ResponseEntity.ok(isTrue);
    }

    //댓글 목록 조회
    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<CommentDto>> commentList(@PathVariable Long boardId) {
        List<CommentDto> list = commentService.getCommentList(boardId);
        return ResponseEntity.ok(list);
    }

    // 댓글 상세 조회
    @GetMapping("/search")
    public ResponseEntity<List<CommentDto>> commentSearch(@PathVariable String keyword) {
        List<CommentDto> list = commentService.getCommentList(keyword);
        return ResponseEntity.ok(list);
    }
}
