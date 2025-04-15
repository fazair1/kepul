package com.juaracoding.kepul.repositories;

import com.juaracoding.kepul.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepo extends JpaRepository<Status, Long> {

    public Page<Status> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    public List<Status> findByNamaContainsIgnoreCase(String nama);

}
