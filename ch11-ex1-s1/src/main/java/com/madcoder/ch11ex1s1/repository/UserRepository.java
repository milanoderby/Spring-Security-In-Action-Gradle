package com.madcoder.ch11ex1s1.repository;

import com.madcoder.ch11ex1s1.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByUsername(String username);
}