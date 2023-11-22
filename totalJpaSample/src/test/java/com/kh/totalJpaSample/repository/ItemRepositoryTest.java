package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.constant.ItemSellStatus;
import com.kh.totalJpaSample.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")

class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    public void createItemList() {
        for (int i = 1; i <= 10; i++ ) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 * i);
            item.setItemDetail("테스트용 상품입니다." + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100 + i);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item saveItem = itemRepository.save(item);
            System.out.println(saveItem);
        }
    }
    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNameTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemName("테스트 상품5");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void create() {
        Item item =  new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트용 상품입니다.");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item saveItem = itemRepository.save(item);
        System.out.println(saveItem);
    }

    @Test
    @DisplayName("상품명 or 상품 상세 설명")
    public void findByItemNameOrItemDetail() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("테스트 상품1", "테스트용 상품입니다.5");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("주어진 가격보다 낮은 제품")
    public void findByPriceLessThanTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(40000);
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    // 정렬하기
    @Test
    @DisplayName("가격 내림차순 정렬")
    public void findAllByOrderByPriceDescTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findAllByOrderByPriceDesc();
        for (Item item : itemList) {
            System.out.println(item);
        }
    }


    @Test
    @DisplayName("Query를 이용한 상품 조회 테스트")
    public void priceSortingTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findItemDetail("테스트");
        for(Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("Native Query 테스트")
    public void priceSortingNativeTest() {
        List<Item> itemList = itemRepository.priceSortingNative("상품");
        for(Item item : itemList) {
            System.out.println(item);
        }
    }
}