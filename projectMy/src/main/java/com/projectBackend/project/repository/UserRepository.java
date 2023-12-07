package com.projectBackend.project.repository;

import com.projectBackend.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String email);
    boolean existsByUserEmail(String email);
    Optional<User> findByUserEmailAndUserPassword(String email, String password);
    boolean existsByUserNickname(String userNickname);

}
