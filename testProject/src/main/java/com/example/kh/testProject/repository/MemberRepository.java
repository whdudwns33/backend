package com.example.kh.testProject.repository;

import com.example.kh.testProject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
// 인터페이스로 상속 받아 사용하는 레지토리
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Member> findByEmailAndPassword(String email, String password);
}
