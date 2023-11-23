package com.example.kh.testProject.controller;

import com.example.kh.testProject.dto.CategoryDto;
import com.example.kh.testProject.service.CategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private CategoryService categoryService;
    // 카테고리 등록
    @PostMapping("/new")
    public ResponseEntity<Boolean> categoryRegister(@RequestBody CategoryDto categoryDto) {
        boolean isTrue = categoryService.saveCategory(categoryDto);
        return ResponseEntity.ok(isTrue);
    }

    // 카테고리 수정
    @PostMapping("/modify/{id}")
    public ResponseEntity<Boolean> categoryModify(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        boolean isTrue = categoryService.modifyCategory(id, categoryDto);
        return ResponseEntity.ok(isTrue);
    }

    // 카테고리 삭제
    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> categoryDelete(@PathVariable Long id) {
        boolean isTrue = categoryService.deleteCategory(id);
        return ResponseEntity.ok(isTrue);
    }

    // 카테고리 전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto>> categoriesList() {
        List<CategoryDto> categoryDtoList = categoryService.getCategoryList();
        return ResponseEntity.ok(categoryDtoList);
    }

    // 특정 카테고리 조회
    @GetMapping("/list/{id}")
    public ResponseEntity<CategoryDto> categoryList(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getSimpleCategory(id);
        return ResponseEntity.ok(categoryDto);
    }
}
