package com.juaracoding.kepul.controller;

import com.juaracoding.kepul.config.OtherConfig;
import com.juaracoding.kepul.dto.validation.ValTransactionDTO;
import com.juaracoding.kepul.service.TransactionService;
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
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Member')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValTransactionDTO valTransactionDTO, HttpServletRequest request) {
        return transactionService.save(transactionService.convertToEntity(valTransactionDTO), request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Member')")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @Valid @RequestBody ValTransactionDTO valTransactionDTO, HttpServletRequest request) {
        return transactionService.update(id, transactionService.convertToEntity(valTransactionDTO), request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id, HttpServletRequest request){
        return transactionService.delete(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Member')")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getPageDefault(), Sort.by("id"));

        return transactionService.findAll(pageable, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        return transactionService.findById(id, request);
    }

    @GetMapping("/{sort}/{sortBy}/{page}")
    @PreAuthorize("hasAuthority('Admin')")
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

        return transactionService.findByParam(pageable, column, value, request);
    }

    @GetMapping("/pdf")
    @PreAuthorize("hasAuthority('Admin')")
    public void downloadPdf(
            @RequestParam(value = "column") String column,
            @RequestParam(value = "value") String value,
            HttpServletRequest request,
            HttpServletResponse response){
        transactionService.generateToPDFManual(column,value,request,response);
    }

    private String sortColumnByMap(String sortBy){
        switch (sortBy){
            case "divisi":sortBy = "divisi";break;
            case "admin":sortBy = "admin";break;
            case "status":sortBy = "status";break;
            default:sortBy = "id";
        }
        return sortBy;
    }

}
