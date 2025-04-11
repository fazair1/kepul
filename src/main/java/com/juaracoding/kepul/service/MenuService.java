package com.juaracoding.kepul.service;

import com.juaracoding.kepul.core.IService;
import com.juaracoding.kepul.model.GroupMenu;
import com.juaracoding.kepul.model.Menu;
import com.juaracoding.kepul.repositories.MenuRepo;
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
public class MenuService implements IService<Menu> {

    @Autowired
    private MenuRepo menuRepo;

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> save(Menu menu, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, Menu menu, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        List<Menu> list = menuRepo.findAll();

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
