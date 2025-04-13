package com.juaracoding.kepul.repositories;

import com.juaracoding.kepul.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    public List<Product> findByNamaContainsIgnoreCase(String nama);

}
