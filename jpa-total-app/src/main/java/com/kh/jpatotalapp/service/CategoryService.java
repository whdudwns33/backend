package com.kh.jpatotalapp.service;
import com.kh.jpatotalapp.dto.CategoryDto;
import com.kh.jpatotalapp.entity.Category;
import com.kh.jpatotalapp.entity.Member;
import com.kh.jpatotalapp.repository.CategoryRepository;
import com.kh.jpatotalapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    // 카테고리 등록
    public boolean saveCategory(CategoryDto categoryDto) {
        try {
            Category category = new Category();
            Member member = memberRepository.findByEmail(categoryDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            category.setCategoryName(categoryDto.getCategoryName());
            category.setMember(member);
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 카테고리 수정
    public boolean modifyCategory(Long id, CategoryDto categoryDto) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
            );
            Member member = memberRepository.findByEmail(categoryDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            category.setCategoryName(categoryDto.getCategoryName());
            category.setCategoryId(categoryDto.getCategoryId());
            category.setMember(member);
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 카테고리 삭제
    public boolean deleteCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
            );
            categoryRepository.delete(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 카테고리 목록 조회
    public List<CategoryDto> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for(Category category : categories) {
            categoryDtos.add(convertEntityToDto(category));
        }
        return categoryDtos;
    }
    // 엔티티를 DTO로 변환하는 메서드
    private CategoryDto convertEntityToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setEmail(category.getMember().getEmail());
        return categoryDto;
    }
}
