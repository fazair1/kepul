package com.juaracoding.kepul.controller;

import com.juaracoding.kepul.config.OtherConfig;
import com.juaracoding.kepul.dto.validation.ValRegisDTO;
import com.juaracoding.kepul.dto.validation.ValTransactionDTO;
import com.juaracoding.kepul.dto.validation.ValUserDTO;
import com.juaracoding.kepul.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValUserDTO valUserDTO, HttpServletRequest request) {
        return userService.save(userService.convertToEntity(valUserDTO), request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Member')")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @Valid @RequestBody ValUserDTO valUserDTO, HttpServletRequest request) {
        return userService.update(id, userService.convertToEntity(valUserDTO), request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id, HttpServletRequest request){
        return userService.delete(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getPageDefault(), Sort.by("id"));

        return userService.findAll(pageable, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        return userService.findById(id, request);
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

        return userService.findByParam(pageable, column, value, request);
    }

    private String sortColumnByMap(String sortBy){
        switch (sortBy){
            case "username": sortBy = "username";break;
            case "email": sortBy = "email";break;
            case "alamat": sortBy = "alamat";break;
            case "nohp": sortBy = "nohp";break;
            case "nama": sortBy = "nama";break;
            default:sortBy = "id";
        }
        return sortBy;
    }

}
