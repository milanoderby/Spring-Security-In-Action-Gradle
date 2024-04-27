package com.madcoder.ch06ex1.service;

import com.madcoder.ch06ex1.entity.Product;
import com.madcoder.ch06ex1.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}