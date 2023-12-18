package com.projectBackend.project.controller;


import com.projectBackend.project.dto.CommunityCategoryDTO;
import com.projectBackend.project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CommunityCategoryController {
    private final CategoryService categoryService;
    // 카테고리 등록
    @PostMapping("/new")
    public ResponseEntity<Boolean> categoryRegister(@RequestBody CommunityCategoryDTO categoryDTO) {
        boolean isTrue = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.ok(true);
    }
    // 카테고리 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<Boolean> categoryModify(@PathVariable Long id, @RequestBody CommunityCategoryDTO categoryDTO) {
        boolean isTrue = categoryService.modifyCategory(id, categoryDTO);
        return ResponseEntity.ok(true);
    }
    // 카테고리 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> categoryDelete(@PathVariable Long id) {
        boolean isTrue = categoryService.deleteCategory(id);
        return ResponseEntity.ok(true);
    }
    // 카테고리 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<CommunityCategoryDTO>> categoryList() {
        List<CommunityCategoryDTO> list = categoryService.getCategoryList();
        return ResponseEntity.ok(list);
    }
}
