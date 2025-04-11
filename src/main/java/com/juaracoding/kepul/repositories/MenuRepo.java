package com.juaracoding.kepul.repositories;

import com.juaracoding.kepul.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepo extends JpaRepository<Menu, Long> {

    public Page<Menu> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    public List<Menu> findByNamaContainsIgnoreCase(String nama);

    public Page<Menu> findByPathContainsIgnoreCase(String nama, Pageable pageable);
    public List<Menu> findByPathContainsIgnoreCase(String nama);

    @Query(value = "SELECT x FROM Menu x WHERE lower(x.groupMenu.nama) LIKE lower(concat('%',?1,'%')) ")
    public Page<Menu> cariGroup(String nama, Pageable pageable);

    @Query(value = "SELECT x FROM Menu x WHERE lower(x.groupMenu.nama) LIKE lower(concat('%',?1,'%')) ")
    public List<Menu> cariGroup(String nama);

    /** digunakan hanya untuk unit testing */
    public Optional<Menu> findTopByOrderByIdDesc();

}
