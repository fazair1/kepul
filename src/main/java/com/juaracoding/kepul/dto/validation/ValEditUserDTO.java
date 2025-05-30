package com.juaracoding.kepul.dto.validation;

import com.juaracoding.kepul.dto.relation.RelAksesDTO;
import jakarta.validation.constraints.Pattern;

public class ValEditUserDTO {

    @Pattern(regexp = "^([a-z0-9\\.]{4,20})$",
            message = "Format Huruf kecil ,numeric dan titik saja min 4 max 20 karakter, contoh : fauzan.123")
    private String username;

    private String password;

    @Pattern(regexp = "^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?![.])[a-zA-Z0-9._%+-]+(?:(?<!\\\\)[.][a-zA-Z0-9-]+)*?)@[a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,50})+$",
            message = "Format tidak valid contoh : user_name123@sub.domain.com")
    private String email;

    @Pattern(regexp = "^[a-zA-Z\\s]{4,50}$",message = "Hanya Alfabet dan spasi Minimal 4 Maksimal 50")
    private String nama;

    private RelAksesDTO akses;

    private Boolean isRegistered;

    public Boolean getRegistered() {
        return isRegistered;
    }

    public void setRegistered(Boolean registered) {
        isRegistered = registered;
    }

    public RelAksesDTO getAkses() {
        return akses;
    }

    public void setAkses(RelAksesDTO akses) {
        this.akses = akses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
