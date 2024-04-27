package com.madcoder.ch06ex1.repository;

import com.madcoder.ch06ex1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
