package com.chitchat.repository;

import com.chitchat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNameAndPassword(String userName, String password);
}
