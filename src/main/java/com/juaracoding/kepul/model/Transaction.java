package com.juaracoding.kepul.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MstTransaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTransaction")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDStatus", foreignKey = @ForeignKey(name = "fk-transaction-to-status"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "IDDivision",nullable = false, foreignKey = @ForeignKey(name = "fk-transaction-to-user1"))
    private User divisionId;

    @ManyToOne
    @JoinColumn(name = "IDAdmin", foreignKey = @ForeignKey(name = "fk-transaction-to-user2"))
    private User adminId;

    @Column(name = "IsDeleted",columnDefinition = ("bit default 0"))
    private Boolean isDeleted=false;

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
    private Long modifiedBy;

    @Column(name = "ModifiedDate", insertable = false)
    @CreationTimestamp
    private LocalDateTime modifiedDate;

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getAdminId() {
        return adminId;
    }

    public void setAdminId(User adminId) {
        this.adminId = adminId;
    }

    public User getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(User divisionId) {
        this.divisionId = divisionId;
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
