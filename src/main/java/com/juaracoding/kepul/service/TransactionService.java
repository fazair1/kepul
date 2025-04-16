package com.juaracoding.kepul.service;

import com.juaracoding.kepul.config.OtherConfig;
import com.juaracoding.kepul.core.IService;
import com.juaracoding.kepul.dto.response.RespProductCategoryDTO;
import com.juaracoding.kepul.dto.response.RespProductDTO;
import com.juaracoding.kepul.dto.response.RespTransactionDTO;
import com.juaracoding.kepul.dto.validation.ValProductDTO;
import com.juaracoding.kepul.dto.validation.ValTransactionDTO;
import com.juaracoding.kepul.handler.GlobalResponse;
import com.juaracoding.kepul.model.*;
import com.juaracoding.kepul.repositories.ProductRepo;
import com.juaracoding.kepul.repositories.StatusRepo;
import com.juaracoding.kepul.repositories.TransactionRepo;
import com.juaracoding.kepul.repositories.UserRepo;
import com.juaracoding.kepul.security.RequestCapture;
import com.juaracoding.kepul.util.GlobalFunction;
import com.juaracoding.kepul.util.LoggingFile;
import com.juaracoding.kepul.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *  Platform Code  - KPL
 *  Modul Code - 03
 *  FV - FE
 */

@Service
@Transactional
public class TransactionService implements IService<Transaction> {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> save(Transaction transaction, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        if (transaction == null) {
            return GlobalResponse.dataTidakValid("KPL03FV001",request);
        }

        try {
            Optional<User> optionalUser = userRepo.findById(Long.parseLong(mapToken.get("userId").toString()));
            User nextUser = optionalUser.get();

            List<Status> statusList = statusRepo.findByNamaContainsIgnoreCase("Waiting for Approval");
            if (statusList.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan("KPL03FV002",request);
            }
            Status nextStatus = statusList.get(0);
            transaction.setStatus(nextStatus);
            transaction.setDivisionId(nextUser);
            transaction.setStatus(nextStatus);
            transaction.setCreatedBy(Long.parseLong(mapToken.get("userId").toString()));
            transactionRepo.save(transaction);
        }catch (Exception e) {
            LoggingFile.logException("TransactionService","save(Transaction transaction, HttpServletRequest request) -- Line 86 "+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDisimpan("KPL03FE003",request);

        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Transaction transaction, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        if (transaction == null) {
            return GlobalResponse.dataTidakValid("KPL03FV011",request);
        }

        try {
            Optional<Transaction> optionalTransaction = transactionRepo.findById(id);
            if (!optionalTransaction.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL03FV012",request);
            }

            Optional<Status> optionalStatus = statusRepo.findById(transaction.getStatus().getId());
            if (!optionalStatus.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL03FV013",request);
            }

            LocalDateTime now = LocalDateTime.now();
            Optional<User> optionalUser = userRepo.findById(Long.parseLong(mapToken.get("userId").toString()));
            User nextUser = optionalUser.get();

            Transaction nextTransaction = optionalTransaction.get();
            nextTransaction.setModifiedBy(Long.parseLong(mapToken.get("userId").toString()));
            nextTransaction.setModifiedDate(now);

            if (transaction.getStatus().getNama().equals("Waiting for Approval")) {
                nextTransaction.setLtProduct(transaction.getLtProduct());
            }
            if (transaction.getStatus().getNama().equals("Approved") && nextUser.getAkses().getNama().equals("Admin")) {
                nextTransaction.setAdminId(nextUser);
                nextTransaction.setStatus(transaction.getStatus());

                for (Product product : nextTransaction.getLtProduct()) {
                    Optional<Product> optionalProduct = productRepo.findById(product.getId());
                    Product nextProduct = optionalProduct.get();
                    nextProduct.setDeleted(true);
                }
            }
        }catch (Exception e) {
            LoggingFile.logException("TransactionService","update(Long id, Transaction transaction, HttpServletRequest request) -- Line 134 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDiubah("KPL03FE014",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<Transaction> optionalTransaction = transactionRepo.findById(id);
            if (!optionalTransaction.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL03FV021",request);
            }
            LocalDateTime now = LocalDateTime.now();

            Transaction nextTransaction = optionalTransaction.get();
            nextTransaction.setModifiedBy(Long.parseLong(mapToken.get("userId").toString()));
            nextTransaction.setModifiedDate(now);
            nextTransaction.setDeleted(true);

        }catch (Exception e) {
            LoggingFile.logException("TransactionService","delete(Long id, HttpServletRequest request) -- Line 157 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDihapus("KPL03FE022",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        Optional<User> optionalUser = userRepo.findById(Long.parseLong(mapToken.get("userId").toString()));
        User nextUser = optionalUser.get();

        Page<Transaction> page = null;
        List<Transaction> list = null;
        if (nextUser.getAkses().getNama().equals("Member")) {
            page = transactionRepo.cariDivisi(nextUser.getUsername(), pageable);
        }
        else {
            page = transactionRepo.findAllByIsDeletedFalse(pageable);
        }
        list = page.getContent();
        List<RespTransactionDTO> lt = convertToRespTransactionDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<Transaction> optionalTransaction = null;
        try {
            optionalTransaction = transactionRepo.findById(id);
            if (!optionalTransaction.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL03FV041",request);
            }
        }catch (Exception e) {
            LoggingFile.logException("TransactionService","findById(Long id, HttpServletRequest request) -- Line 193 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.terjadiKesalahan("KPL03FE042",request);
        }
        return GlobalResponse.dataDitemukan(modelMapper.map(optionalTransaction.get(),RespTransactionDTO.class),request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Transaction> page = null;
        List<Transaction> list = null;
        switch (columnName) {
            case "divisi": page = transactionRepo.cariDivisi(value,pageable);break;
            case "admin": page = transactionRepo.cariAdmin(value,pageable);break;
            case "status": page = transactionRepo.cariStatus(value,pageable);break;
            default: page = transactionRepo.findAll(pageable);
        }
        list = page.getContent();
        List<RespTransactionDTO> lt = convertToRespTransactionDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,columnName,value),
                request);
    }

    public Transaction convertToEntity (ValTransactionDTO valTransactionDTO) {
//        GroupMenu groupMenu = new GroupMenu();
//        groupMenu.setNama(valGroupMenuDTO.getNama());
//        groupMenu.setDeskripsi(valGroupMenuDTO.getDeskripsi());
        Transaction transaction = modelMapper.map(valTransactionDTO, Transaction.class);
        return transaction;
    }

    public List<RespTransactionDTO> convertToRespTransactionDTO (List<Transaction> transactions) {
//        List<RespGroupMenuDTO> respGroupMenuDTOList = new ArrayList<>();
//
//        for (GroupMenu groupMenu: groupMenus) {
//            RespGroupMenuDTO respGroupMenuDTO = new RespGroupMenuDTO();
//            respGroupMenuDTO.setId(groupMenu.getId());
//            respGroupMenuDTO.setNama(groupMenu.getNama());
//            respGroupMenuDTO.setDeskripsi(groupMenu.getDeskripsi());
//            respGroupMenuDTOList.add(respGroupMenuDTO);
//        }
        List<RespTransactionDTO> respTransactionDTOList = modelMapper.map(transactions, new TypeToken<List<RespTransactionDTO>>() {}.getType());
        return respTransactionDTOList;
    }

}
