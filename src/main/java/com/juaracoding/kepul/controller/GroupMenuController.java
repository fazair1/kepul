package com.juaracoding.kepul.controller;

import com.juaracoding.kepul.model.GroupMenu;
import com.juaracoding.kepul.repositories.GroupMenuRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("group-menu")
public class GroupMenuController {

    @Autowired
    GroupMenuRepo groupMenuRepo;

    @GetMapping
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        List<GroupMenu> groupMenu = groupMenuRepo.findAll();
        Map<String,Object> map = new HashMap<>();
        map.put("message","test");
        map.put("status",HttpStatus.OK.value());
        map.put("data",groupMenu);
        map.put("timestamp", LocalDateTime.now());
        map.put("success","true");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
