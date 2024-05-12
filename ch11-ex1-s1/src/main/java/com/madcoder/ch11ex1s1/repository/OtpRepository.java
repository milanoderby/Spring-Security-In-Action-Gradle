package com.madcoder.ch11ex1s1.repository;

import com.madcoder.ch11ex1s1.entity.Otp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, String> {

    Optional<Otp> findOtpByUsername(String username);
}