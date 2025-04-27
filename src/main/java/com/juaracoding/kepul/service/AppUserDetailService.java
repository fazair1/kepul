package com.juaracoding.kepul.service;

import com.juaracoding.kepul.config.JwtConfig;
import com.juaracoding.kepul.config.OtherConfig;
import com.juaracoding.kepul.dto.validation.ValLoginDTO;
import com.juaracoding.kepul.dto.validation.ValRegisDTO;
import com.juaracoding.kepul.dto.validation.ValVerifyOTPRegisDTO;
import com.juaracoding.kepul.handler.GlobalResponse;
import com.juaracoding.kepul.model.Akses;
import com.juaracoding.kepul.model.User;
import com.juaracoding.kepul.repositories.UserRepo;
import com.juaracoding.kepul.security.BcryptImpl;
import com.juaracoding.kepul.security.Crypto;
import com.juaracoding.kepul.security.JwtUtility;
import com.juaracoding.kepul.util.SendMailOTP;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 *  Modul Code - 00
 *  Platform Code - AUT
 */

@Service
@Transactional
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtility jwtUtility;

    private Random random = new Random();

    public ResponseEntity<Object> login(User user, HttpServletRequest request) {
        Optional<User> optUser = userRepo.findByUsername(user.getUsername());
        Map<String,Object> m = new HashMap<>();
        if(!optUser.isPresent()){
            return GlobalResponse.loginBermasalah("AUT00FV001", request);
        }
        User userNext = optUser.get();
        if(!userNext.getRegistered()){
            return GlobalResponse.loginBermasalah("AUT00FV002", request);
        }

        if(!BcryptImpl.verifyHash((user.getUsername()+user.getPassword()),userNext.getPassword())){
            return GlobalResponse.loginBermasalah("AUT00FV003", request);
        }
        Map<String,Object> m1 = new HashMap<>();
        m1.put("id", userNext.getId());
        m1.put("phn", userNext.getNoHp());
        m1.put("ml", userNext.getEmail());
        m1.put("nl", userNext.getNama());

        /** khusus testing automation */
        String token = jwtUtility.doGenerateToken(m1,userNext.getUsername());
        if(JwtConfig.getTokenEncryptEnable().equals("y")){
            token = Crypto.performEncrypt(token);
        }
//        List<MenuLoginDTO> ltMenu = modelMapper.map(userNext.getAkses().getLtMenu(),new TypeToken<List<MenuLoginDTO>>(){}.getType());
        m.put("token",token);
//        m.put("menu",userNext.getAkses().getLtMenu());
        m.put("akses", userNext.getAkses().getNama());

//        m.put("menu",new ArrayList<>());
        return GlobalResponse.dataDitemukan(m, request);
    }

    public ResponseEntity<Object> regis(User user, HttpServletRequest request) {
        Optional<User> optUser = userRepo.findByUsername(user.getUsername());
        Map<String,Object> m = new HashMap<>();
        /** control flow yg ini jika user belum pernah sama sekali melakukan registrasi ,
         *  sehingga proses nya tinggal simpan saja
         */
        int intOtp = random.nextInt(111111,999999);
        if(!optUser.isPresent()){
            Optional<User> optCheckEmailUser = userRepo.findByEmailAndIsRegistered(user.getEmail(),true);
            if(optCheckEmailUser.isPresent()){
                return GlobalResponse.emailTeregistrasi("AUT00FV012", request);
            }

            user.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
            user.setOtp(BcryptImpl.hash(String.valueOf(intOtp)));
            Akses akses = new Akses();
            akses.setId(2L);//default untuk relasi nya ke akses, jadi user yang melakukan registrasi otomatis mendapatkan akses sebagai member
            user.setAkses(akses);
            userRepo.save(user);
            m.put("email",user.getEmail());
        }else{
            User userNext = optUser.get();
            if(userNext.getRegistered()){
                return GlobalResponse.sudahTeregistrasi("AUT00FV011", request);
            }
            Optional<User> optCheckEmailUser = userRepo.findByEmailAndIsRegistered(user.getEmail(),true);
            if(optCheckEmailUser.isPresent()){
                return GlobalResponse.emailTeregistrasi("AUT00FV012", request);
            }
            Optional<User> optCheckNoHp = userRepo.findByNoHpAndIsRegistered(user.getNoHp(),true);
            if(optCheckNoHp.isPresent()){
                return GlobalResponse.noHpTeregistrasi("AUT00FV013", request);
            }
            userNext.setOtp(BcryptImpl.hash(String.valueOf(intOtp)));
            userNext.setEmail(user.getEmail());
            userNext.setNoHp(user.getNoHp());
            userNext.setAlamat(user.getAlamat());
            userNext.setNama(user.getNama());
            userNext.setTanggalLahir(user.getTanggalLahir());
            userNext.setModifiedBy(userNext.getId());
            userNext.setPassword(user.getUsername()+user.getPassword());
            m.put("email",user.getEmail());
        }

        if(OtherConfig.getEnableAutomationTest().equals("y")){
            m.put("otp",String.valueOf(intOtp));//ini digunakan hanya untuk automation testing ataupun unit testing saja....
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

    public ResponseEntity<Object> verifyRegis(User user, HttpServletRequest request) {
        Optional<User> optUser = userRepo.findByEmail(user.getEmail());
        if(!optUser.isPresent()){
            return GlobalResponse.dataTidakValid("AUT00FV021", request);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data Tidak Valid !!");
        }
        User userNext = optUser.get();
        /** OTP nya sudah Valid */
        if(!BcryptImpl.verifyHash(user.getOtp(),userNext.getOtp())){
            return GlobalResponse.dataTidakValid("AUT00FV022", request);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP Tidak Valid, Cek Email Anda !!");
        }
        userNext.setRegistered(true);
        userNext.setModifiedBy(userNext.getId());

        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opUser = userRepo.findByUsername(username);
        if(!opUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        User user = opUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),user.getAuthorities());
    }

    public User convertToEntity (ValLoginDTO valLoginDTO) {
        return modelMapper.map(valLoginDTO, User.class);
    }
    public User convertToEntity (ValRegisDTO valRegisDTO) {
        return modelMapper.map(valRegisDTO, User.class);
    }
    public User convertToEntity (ValVerifyOTPRegisDTO valVerifyOTPRegisDTO) {
        return modelMapper.map(valVerifyOTPRegisDTO, User.class);
    }

}
