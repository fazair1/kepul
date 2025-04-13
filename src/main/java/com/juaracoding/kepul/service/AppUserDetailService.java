package com.juaracoding.kepul.service;

import com.juaracoding.kepul.config.JwtConfig;
import com.juaracoding.kepul.dto.validation.ValLoginDTO;
import com.juaracoding.kepul.handler.GlobalResponse;
import com.juaracoding.kepul.model.User;
import com.juaracoding.kepul.repositories.UserRepo;
import com.juaracoding.kepul.security.BcryptImpl;
import com.juaracoding.kepul.security.Crypto;
import com.juaracoding.kepul.security.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

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

}
