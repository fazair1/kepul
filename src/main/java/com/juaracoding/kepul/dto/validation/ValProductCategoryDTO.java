package com.juaracoding.kepul.dto.validation;

import com.juaracoding.kepul.util.ConstantsMessage;
import jakarta.validation.constraints.Pattern;

public class ValProductCategoryDTO {

    @Pattern(regexp = "^[\\w\\s]{3,50}$", message = ConstantsMessage.VAL_PRODUCT_CATEGORY_NAMA)
    private String nama;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
