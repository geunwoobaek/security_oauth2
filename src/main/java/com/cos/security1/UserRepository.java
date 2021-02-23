package com.cos.security1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUsernameAndPassword(String Username,String Password);
    User findByUsername(String Username);
}
