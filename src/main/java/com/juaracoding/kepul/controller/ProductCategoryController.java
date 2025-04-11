package com.juaracoding.kepul.controller;

import com.juaracoding.kepul.config.OtherConfig;
import com.juaracoding.kepul.dto.validation.ValProductCategoryDTO;
import com.juaracoding.kepul.service.ProductCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product-category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping
//    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValProductCategoryDTO valProductCategoryDTO, HttpServletRequest request) {
        return productCategoryService.save(productCategoryService.convertToEntity(valProductCategoryDTO), request);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @Valid @RequestBody ValProductCategoryDTO valProductCategoryDTO, HttpServletRequest request) {
        return productCategoryService.update(id, productCategoryService.convertToEntity(valProductCategoryDTO), request);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id, HttpServletRequest request){
        return productCategoryService.delete(id,request);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getPageDefault(), Sort.by("id"));

        return productCategoryService.findAll(pageable, request);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        return productCategoryService.findById(id, request);
    }

    @GetMapping("/{sort}/{sortBy}/{page}")
//    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> findByParam(
            @PathVariable(value = "sort") String sort,
            @PathVariable(value = "sortBy") String sortBy,
            @PathVariable(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "column") String column,
            @RequestParam(value = "value") String value,
            HttpServletRequest request){

        Pageable pageable = null;
        sortBy = sortColumnByMap(sortBy);
        switch (sort) {
            case "asc":pageable = PageRequest.of(page, size, Sort.by(sortBy));break;
            default: pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        return productCategoryService.findByParam(pageable, column, value, request);
    }

    private String sortColumnByMap(String sortBy){
        switch (sortBy){
            case "nama":sortBy = "nama";break;
            default:sortBy = "id";
        }
        return sortBy;
    }

}
