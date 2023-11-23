package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.constant.ItemSellStatus;
import com.kh.totalJpaSample.entity.Item;
import com.kh.totalJpaSample.entity.Member;
import com.kh.totalJpaSample.entity.Order;
import com.kh.totalJpaSample.entity.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @PersistenceContext
    EntityManager em;

    public Item createItem(int i) {
        Item item = new Item();
        item.setItemName("테스트 아이템" + i);
        item.setPrice(1000);
        item.setItemDetail("아이템 설명" + i);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(10);
        return item;
    }


    @Test
    @DisplayName("영속성 전의 테스트")
    public void cascadeTest() {
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Item item = this.createItem(i);
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(10000);
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }
        // 엔티티를 저장하면서 DB에 반영
        orderRepository.saveAndFlush(order);
        // 영속성 상태를 초기화
        em.clear();
        // 주문 엔티티 조회
        Order saveOder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        System.out.println("saveOrder : " + saveOder);
        // Order 객체의 OrderItem 개수가 3개 인지 확인
        assertEquals(3, saveOder.getOrderItemList().size());
    }

    public Order createOrder() {
        Order order = new Order();
        for(int i = 0; i < 3; i++) {
            Item item = createItem(i);
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }
        Member member = new Member();
        member.setName("임릉이");
        member.setEmail("jojo6807@naver.com");
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("고아 객체 제거 테스트")
    public void orphanRemovalTest() {
        System.out.println("고아객체 테스트 삭제");
        Order order = this.createOrder();
        order.getOrderItemList().remove(0);
        em.flush();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        // 오더 리스트의 0번째 객체의 아이디를 가져오기
        Long orderItemId = order.getOrderItemList().get(0).getId();
        em.flush();
        em.clear();
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
        log.warn(String.valueOf(orderItem.getOrder().getClass()));
        log.warn("------------------------------------------------");
        log.warn(String.valueOf(orderItem.getOrder().getOrderDate()));

    }


}