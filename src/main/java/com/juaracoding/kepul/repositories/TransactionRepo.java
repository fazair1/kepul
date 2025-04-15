package com.juaracoding.kepul.repositories;

import com.juaracoding.kepul.model.Product;
import com.juaracoding.kepul.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    public Page<Transaction> findAllByIsDeletedFalse(Pageable pageable);
    public List<Transaction> findAllByIsDeletedFalse();

    @Query(value = "SELECT x FROM Transaction x WHERE lower(x.divisionId.username) LIKE lower(concat('%',?1,'%')) ")
    public Page<Transaction> cariDivisi(String nama, Pageable pageable);
    @Query(value = "SELECT x FROM Transaction x WHERE lower(x.divisionId.username) LIKE lower(concat('%',?1,'%')) ")
    public List<Transaction> cariDivisi(String nama);

    @Query(value = "SELECT x FROM Transaction x WHERE lower(x.adminId.username) LIKE lower(concat('%',?1,'%')) ")
    public Page<Transaction> cariAdmin(String nama, Pageable pageable);
    @Query(value = "SELECT x FROM Transaction x WHERE lower(x.adminId.username) LIKE lower(concat('%',?1,'%')) ")
    public List<Transaction> cariAdmin(String nama);

    @Query(value = "SELECT x FROM Transaction x WHERE lower(x.status.nama) LIKE lower(concat('%',?1,'%')) ")
    public Page<Transaction> cariStatus(String nama, Pageable pageable);
    @Query(value = "SELECT x FROM Transaction x WHERE lower(x.status.nama) LIKE lower(concat('%',?1,'%')) ")
    public List<Transaction> cariStatus(String nama);

}
