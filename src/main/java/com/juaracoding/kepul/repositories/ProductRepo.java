package com.juaracoding.kepul.repositories;

import com.juaracoding.kepul.model.Product;
import com.juaracoding.kepul.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    public Page<Product> findByNamaContainsIgnoreCaseAndIsDeletedFalse(String nama, Pageable pageable);
    public List<Product> findByNamaContainsIgnoreCaseAndIsDeletedFalse(String nama);

    public Page<Product> findByDeskripsiContainsIgnoreCaseAndIsDeletedFalse(String nama, Pageable pageable);
    public List<Product> findByDeskripsiContainsIgnoreCaseAndIsDeletedFalse(String nama);

    public Page<Product> findAllByIsDeletedFalse(Pageable pageable);
    public List<Product> findAllByIsDeletedFalse();

    @Query(value = "SELECT x FROM Product x WHERE lower(x.productCategory.nama) LIKE lower(concat('%',?1,'%')) AND x.isDeleted = false")
    public Page<Product> cariCategory(String nama, Pageable pageable);

    @Query(value = "SELECT x FROM Product x WHERE lower(x.productCategory.nama) LIKE lower(concat('%',?1,'%')) AND x.isDeleted = false")
    public List<Product> cariCategory(String nama);

    public Optional<Product> findTopByOrderByIdDesc();

}
