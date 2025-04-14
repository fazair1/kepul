package com.juaracoding.kepul.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MstTransaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTransaction")
    private Long id;

    @Column(name = "IDDivision", nullable = false)
    private Long divisionId;

    @Column(name = "IDAdmin")
    private Long adminId;

    @ManyToMany
    @JoinTable(name = "TransactionDetail", uniqueConstraints = @UniqueConstraint(name = "unq-transaction-to-product", columnNames = {"IDTransaction", "IDProduct"}),
            joinColumns = @JoinColumn(name = "IDTransaction", foreignKey = @ForeignKey(name = "fk-toTransaction")),
            inverseJoinColumns = @JoinColumn(name = "IDProduct", foreignKey = @ForeignKey(name = "fk-toProduct")))
    private List<Product> ltProduct;

    @Column(name = "CreatedBy",updatable = false, nullable = false)
    private Long createdBy = 1L;

    @Column(name = "CreatedDate",updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "ModifiedBy", insertable = false)
    private Long modifiedBy = 1L;

    @Column(name = "ModifiedDate", insertable = false)
    @CreationTimestamp
    private LocalDateTime modifiedDate;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getLtProduct() {
        return ltProduct;
    }

    public void setLtProduct(List<Product> ltProduct) {
        this.ltProduct = ltProduct;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
