package com.juaracoding.kepul.repositories;

import com.juaracoding.kepul.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {
}
