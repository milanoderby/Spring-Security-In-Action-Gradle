package com.madcoder.ch10ex3.repository;

import com.madcoder.ch10ex3.entity.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findTokenByIdentifier(String identifier);
}
