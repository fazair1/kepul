package com.juaracoding.kepul.controller;

import com.juaracoding.kepul.config.OtherConfig;
import com.juaracoding.kepul.dto.validation.ValProductCategoryDTO;
import com.juaracoding.kepul.dto.validation.ValProductDTO;
import com.juaracoding.kepul.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValProductDTO valProductDTO, HttpServletRequest request) {
        return productService.save(productService.convertToEntity(valProductDTO), request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @Valid @RequestBody ValProductDTO valProductDTO, HttpServletRequest request) {
        return productService.update(id, productService.convertToEntity(valProductDTO), request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id, HttpServletRequest request){
        return productService.delete(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Member')")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getPageDefault(), Sort.by("id"));

        return productService.findAll(pageable, request);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Member')")
    public ResponseEntity<Object> allProduct(HttpServletRequest request){
        return productService.allProduct(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Member')")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        return productService.findById(id, request);
    }

    @GetMapping("/{sort}/{sortBy}/{page}")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Member')")
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

        return productService.findByParam(pageable, column, value, request);
    }

    @GetMapping("/pdf")
    @PreAuthorize("hasAuthority('Admin')")
    public void downloadPdf(
            @RequestParam(value = "column") String column,
            @RequestParam(value = "value") String value,
            HttpServletRequest request,
            HttpServletResponse response){
        productService.generateToPDF(column,value,request,response);
    }

    private String sortColumnByMap(String sortBy){
        switch (sortBy){
            case "nama":sortBy = "nama";break;
            case "deskripsi":sortBy = "deskripsi";break;
            case "category":sortBy = "category";break;
            default:sortBy = "id";
        }
        return sortBy;
    }

}
