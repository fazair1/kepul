package com.juaracoding.kepul.dto.validation;

import com.juaracoding.kepul.dto.relation.RelProductDTO;
import com.juaracoding.kepul.dto.relation.RelStatusDTO;
import com.juaracoding.kepul.dto.relation.RelUserDTO;

import java.util.List;

public class ValTransactionDTO {

//    private RelUserDTO divisionId;

    private List<RelProductDTO> ltProduct;

//    private RelUserDTO adminId;

    private RelStatusDTO status;

//    public RelUserDTO getAdminId() {
//        return adminId;
//    }
//
//    public void setAdminId(RelUserDTO adminId) {
//        this.adminId = adminId;
//    }

    public RelStatusDTO getStatus() {
        return status;
    }

    public void setStatus(RelStatusDTO status) {
        this.status = status;
    }

//    public RelUserMemberDTO getDivisionId() {
//        return divisionId;
//    }
//
//    public void setDivisionId(RelUserMemberDTO divisionId) {
//        this.divisionId = divisionId;
//    }

    public List<RelProductDTO> getLtProduct() {
        return ltProduct;
    }

    public void setLtProduct(List<RelProductDTO> ltProduct) {
        this.ltProduct = ltProduct;
    }
}
