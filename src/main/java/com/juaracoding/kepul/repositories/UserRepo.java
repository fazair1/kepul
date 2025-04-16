package com.juaracoding.kepul.repositories;

import com.juaracoding.kepul.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    /** Select * From MstUser WHERE Email = ? AND IsRegistered = ? */
    public Optional<User> findByEmailAndIsRegistered(String value, Boolean isRegistered);
    public Optional<User> findByEmail(String value);
    public Optional<User> findByNoHpAndIsRegistered(String value, Boolean isRegistered);

    /** Select * From MstUser WHERE Username = ? */
    public Optional<User> findByUsername(String value);

    public Page<User> findByUsernameContainsIgnoreCase(String value, Pageable pageable);
//    public List<User> findByUsername(String value);

    public Page<User> findByEmailContainsIgnoreCase(String value, Pageable pageable);

    public Page<User> findByAlamatContainsIgnoreCase(String value, Pageable pageable);

    public Page<User> findByNoHpContainsIgnoreCase(String value, Pageable pageable);

    public Page<User> findByNamaContainsIgnoreCase(String value, Pageable pageable);

    /** digunakan hanya untuk unit testing */
    public Optional<User> findTopByOrderByIdDesc();


}
