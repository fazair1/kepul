package com.juaracoding.kepul.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MstAkses")
public class Akses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDAkses")
    private Long id;

    @Column(name = "NamaAkses", unique = true, nullable = false, length = 20)
    private String nama;

    @Column(name = "Deskripsi", nullable = false, length = 100)
    private String deskripsi;

//    @ManyToMany
//    @JoinTable(name = "MapAksesMenu", uniqueConstraints = @UniqueConstraint(name = "unq-akses-to-menu", columnNames = {"IDAkses", "IDMenu"}),
//        joinColumns = @JoinColumn(name = "IDAkses", foreignKey = @ForeignKey(name = "fk-toAkses")),
//        inverseJoinColumns = @JoinColumn(name = "IDMenu", foreignKey = @ForeignKey(name = "fk-toMenu")))
//    private List<Menu> ltMenu;

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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<Menu> getLtMenu() {
//        return ltMenu;
//    }
//
//    public void setLtMenu(List<Menu> ltMenu) {
//        this.ltMenu = ltMenu;
//    }

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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}

