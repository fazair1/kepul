package com.juaracoding.kepul.repositories;

import com.juaracoding.kepul.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
