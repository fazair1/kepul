package com.juaracoding.kepul.service;

import com.juaracoding.kepul.config.OtherConfig;
import com.juaracoding.kepul.core.IService;
import com.juaracoding.kepul.dto.response.RespTransactionDTO;
import com.juaracoding.kepul.dto.response.RespUserDTO;
import com.juaracoding.kepul.dto.validation.ValRegisDTO;
import com.juaracoding.kepul.dto.validation.ValUserDTO;
import com.juaracoding.kepul.handler.GlobalResponse;
import com.juaracoding.kepul.model.*;
import com.juaracoding.kepul.repositories.UserRepo;
import com.juaracoding.kepul.security.BcryptImpl;
import com.juaracoding.kepul.security.RequestCapture;
import com.juaracoding.kepul.util.GlobalFunction;
import com.juaracoding.kepul.util.LoggingFile;
import com.juaracoding.kepul.util.SendMailOTP;
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
import java.util.*;

/**
 *  Platform Code  - KPL
 *  Modul Code - 04
 *  FV - FE
 */

@Service
@Transactional
public class UserService implements IService<User> {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    private Random random = new Random();

    @Override
    public ResponseEntity<Object> save(User user, HttpServletRequest request) {
        Optional<User> optUser = userRepo.findByUsername(user.getUsername());
        Map<String,Object> m = new HashMap<>();
        /** control flow yg ini jika user belum pernah sama sekali melakukan registrasi ,
         *  sehingga proses nya tinggal simpan saja
         */
        int intOtp = random.nextInt(111111,999999);
        if(!optUser.isPresent()){
            user.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
            user.setOtp(BcryptImpl.hash(String.valueOf(intOtp)));
            userRepo.save(user);
            m.put("email",user.getEmail());
        }else{
            return GlobalResponse.emailTeregistrasi("AUT04FV001", request);
        }

        if(OtherConfig.getEnableAutomationTest().equals("y")){
            m.put("otp",intOtp);//ini digunakan hanya untuk automation testing ataupun unit testing saja....
        }
        if(OtherConfig.getSmtpEnable().equals("y")){
            SendMailOTP.verifyRegisOTP("Verifikasi OTP Registrasi",//di harcode
                    user.getNama(),
                    user.getEmail(),
                    String.valueOf(intOtp)
            );
        }

        return GlobalResponse.dataBerhasilDiregistrasi(m, request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, User user, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        if (user == null) {
            return GlobalResponse.dataTidakValid("KPL04FV011",request);
        }

        try {
            Optional<User> optionalUser = userRepo.findById(id);
            if (!optionalUser.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL04FV012",request);
            }

            LocalDateTime now = LocalDateTime.now();
            User userNext = optionalUser.get();

            Optional<User> optionalCurrUser = userRepo.findById(Long.parseLong(mapToken.get("userId").toString()));
            User currUser = optionalCurrUser.get();

            if (currUser.getAkses().getNama().equals("Admin")) {
                userNext.setAkses(user.getAkses());
            }
            if (!user.getPassword().isEmpty()) {
                userNext.setPassword(user.getUsername()+user.getPassword());
            }
            userNext.setModifiedBy(Long.parseLong(mapToken.get("userId").toString()));
            userNext.setModifiedDate(now);
            userNext.setEmail(user.getEmail());
            userNext.setNoHp(user.getNoHp());
            userNext.setAlamat(user.getAlamat());
            userNext.setNama(user.getNama());
            userNext.setTanggalLahir(user.getTanggalLahir());
            userNext.setUsername(user.getUsername());

        }catch (Exception e) {
            LoggingFile.logException("TransactionService","update(Long id, Transaction transaction, HttpServletRequest request) -- Line 134 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDiubah("KPL04FE013",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<User> optionalUser = userRepo.findById(id);
            if (!optionalUser.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL04FV021",request);
            }
            User userNext = optionalUser.get();
            userRepo.delete(userNext);
        }catch (Exception e) {
            LoggingFile.logException("TransactionService","delete(Long id, HttpServletRequest request) -- Line 157 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.dataGagalDihapus("KPL04FE022",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        Page<User> page = null;
        List<User> list = null;
        page = userRepo.findAll(pageable);

        list = page.getContent();
        List<RespUserDTO> lt = convertToRespUserDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<User> optionalUser = null;
        try {
            optionalUser = userRepo.findById(id);
            if (!optionalUser.isPresent()) {
                return GlobalResponse.dataTidakDitemukan("KPL04FV041",request);
            }
        }catch (Exception e) {
            LoggingFile.logException("TransactionService","findById(Long id, HttpServletRequest request) -- Line 193 "+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.terjadiKesalahan("KPL04FE042",request);
        }
        return GlobalResponse.dataDitemukan(modelMapper.map(optionalUser.get(),RespUserDTO.class),request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<User> page = null;
        List<User> list = null;
        switch (columnName) {
            case "username": page = userRepo.findByUsernameContainsIgnoreCase(value,pageable);break;
            case "email": page = userRepo.findByEmailContainsIgnoreCase(value,pageable);break;
            case "alamat": page = userRepo.findByAlamatContainsIgnoreCase(value,pageable);break;
            case "nohp": page = userRepo.findByNoHpContainsIgnoreCase(value,pageable);break;
            case "nama": page = userRepo.findByNamaContainsIgnoreCase(value,pageable);break;
            default: page = userRepo.findAll(pageable);
        }
        list = page.getContent();
        List<RespUserDTO> lt = convertToRespUserDTO(list);

        return GlobalResponse.dataDitemukan(transformPagination.transformPagination(lt,page,columnName,value),
                request);
    }

    public User convertToEntity (ValUserDTO valUserDTO) {
        return modelMapper.map(valUserDTO, User.class);
    }

    public List<RespUserDTO> convertToRespUserDTO (List<User> users) {
//        List<RespGroupMenuDTO> respGroupMenuDTOList = new ArrayList<>();
//
//        for (GroupMenu groupMenu: groupMenus) {
//            RespGroupMenuDTO respGroupMenuDTO = new RespGroupMenuDTO();
//            respGroupMenuDTO.setId(groupMenu.getId());
//            respGroupMenuDTO.setNama(groupMenu.getNama());
//            respGroupMenuDTO.setDeskripsi(groupMenu.getDeskripsi());
//            respGroupMenuDTOList.add(respGroupMenuDTO);
//        }
        List<RespUserDTO> respUserDTOList = modelMapper.map(users, new TypeToken<List<RespUserDTO>>() {}.getType());
        return respUserDTOList;
    }

}
