package com.juaracoding.kepul.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IReport<G> {

    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response);//091-100
}
