package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.dto.MemberDto;
import com.kh.totalJpaSample.entity.Cart;
import com.kh.totalJpaSample.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
//import lombok.extern.slf4j.Slf4j;

@SpringBootTest // 스프링 컨텍스트를 로드하여 테스트 환경 설정
@Transactional  // 데이터 베이스의 논리적인 작업 단위, 모두 성공이 아니면 롤백
@TestPropertySource(locations = "classpath:application-test.properties")
//@Slf4j // 로깅 데이터를 처리하기 위해서 사용
class CartRepositotyTest {
    @Autowired
    CartRepositoty cartRepositoty;
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext // JPA의 EntityManager를 주입 받음
    EntityManager em;
    // 회원 엔티티
    public Member createMemberInfo() {
        Member member = new Member();
        member.setUserId("asd123");
        member.setPassword("123@@");
        member.setName("유저");
        member.setEmail("ad13@zxc.cm");
        member.setRegDate(LocalDateTime.now());
        return member;
    }

    @Test
    @DisplayName("장바구니 회원 맵핑 테스트")
    public void findCartAndMemberTest() {
        Member member = createMemberInfo();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setCartName("장바구니니니ㅣ닌니");
        cart.setMember(member);
        cartRepositoty.save(cart);

        em.flush(); // 영속성 컨텍스트에 데이터 저장 후, 트랜잭션이 끝날 때 데이터베이스에 기록
        em.clear(); // 영속성 컨텍스트를 비움
        Cart saveCart = cartRepositoty.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);
        System.out.println(saveCart);
    }
}
















