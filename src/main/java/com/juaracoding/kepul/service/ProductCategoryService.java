package com.juaracoding.kepul.service;

import com.juaracoding.kepul.core.IService;
import com.juaracoding.kepul.model.GroupMenu;
import com.juaracoding.kepul.model.ProductCategory;
import com.juaracoding.kepul.repositories.ProductCategoryRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductCategoryService implements IService<ProductCategoryService> {

    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> save(ProductCategoryService productCategoryService, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, ProductCategoryService productCategoryService, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        List<ProductCategory> list = productCategoryRepo.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        return null;
    }
}
