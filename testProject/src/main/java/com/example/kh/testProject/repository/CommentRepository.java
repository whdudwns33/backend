package com.example.kh.testProject.repository;

import com.example.kh.testProject.entity.Board;
import com.example.kh.testProject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByContentContaining(String keyword);
    List<Comment> findByBoard(Board board);
}
