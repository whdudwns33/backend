package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepositoty extends JpaRepository<Cart, Long> {
}
