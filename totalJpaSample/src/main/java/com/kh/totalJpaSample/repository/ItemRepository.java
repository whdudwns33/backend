package com.kh.totalJpaSample.repository;
import com.kh.totalJpaSample.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// 기본적인 CRUD는 JpaRepository에서 정의 되어있음, 페이징 처리도 포함되어 있음
// JPA에 대한 상세 명세를 해줌.
// 이 인터페이스의 상속받는 자식은 하이버네이트임.
// 하이버네이트가 상속받아서 어떠한 쿼리문 수행 역할을 해줌
// JpaRepository을 클릭해보면 이거는 함수가 쿼리문처럼 쓰여있음.
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemName(String itemName);
    // OR 조건 처리
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);
    //LessThan 조건 처리: ~보다 작다. 미만의 개념
    List<Item> findByPriceLessThan(Integer price);
    //OrderBy 정렬
    List<Item> findAllByOrderByPriceDesc();
    // JPQL 쿼리 작성하기 : SQL과 유사한 객체 지향 쿼리 언어.
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findItemDetail(@Param("itemDetail") String itemDetail);
    // nativeQuery 사용하기
    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> priceSortingNative(@Param("itemDetail") String itemDetail);
}
