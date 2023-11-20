package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// bean을 등록해달라고 요청.
@Repository
// 네이밍 규칙에 의해서 API를 작성하면 그에 맞는 쿼리문을 하이버네이트가 구현해줌.
// 제네릭 타입 <객체의 클래스 이름, @Id의 타입>
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 쿼리문 생성. 컬럼 이름과 맞춰야 함. 추가로 규칙이 존재. 예를 들어, 카멜 표기법을 지킨다던가
    Optional<Member> findByEmail(String email);
    Member findByPassword(String password);
    Member findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}
