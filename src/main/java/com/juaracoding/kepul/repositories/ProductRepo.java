package com.juaracoding.kepul.repositories;

import com.juaracoding.kepul.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    public Page<Product> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    public List<Product> findByNamaContainsIgnoreCase(String nama);

    public Page<Product> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);
    public List<Product> findByDeskripsiContainsIgnoreCase(String nama);

    public Page<Product> findAllByIsDeletedFalse(Pageable pageable);
    public List<Product> findAllByIsDeletedFalse();

    @Query(value = "SELECT x FROM Product x WHERE lower(x.productCategory.nama) LIKE lower(concat('%',?1,'%')) ")
    public Page<Product> cariCategory(String nama, Pageable pageable);

    @Query(value = "SELECT x FROM Product x WHERE lower(x.productCategory.nama) LIKE lower(concat('%',?1,'%')) ")
    public List<Product> cariCategory(String nama);

}
