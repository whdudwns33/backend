package com.kh.jpatotalapp.repository;

import com.kh.jpatotalapp.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {


}
