package com.juaracoding.kepul.repositories;

import com.juaracoding.kepul.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {
    /** Select * From MstGroupMenu WHERE toLower(Email) Like toLower('%chihuy%') */
    public Page<ProductCategory> findByNamaContainsIgnoreCase(String nama, Pageable pageable);

    /** Ini untuk Report */
    public List<ProductCategory> findByNamaContainsIgnoreCase(String nama);

    /** digunakan hanya untuk unit testing */
    public Optional<ProductCategory> findTopByOrderByIdDesc();

}
