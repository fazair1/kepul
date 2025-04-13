package com.juaracoding.kepul.controller;

import com.juaracoding.kepul.service.AksesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("akses")
public class AksesController {

    @Autowired
    private AksesService aksesService;

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, 50, Sort.by("id"));

        return aksesService.findAll(pageable, request);
    }

}
