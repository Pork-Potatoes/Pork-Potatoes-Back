package com.matzipuniv.sinchon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByDeleteFlagFalse();
    User findByUserNumAndDeleteFlagFalse(Long userNum);
    User findByEmailAndSocialLogin(String email, String socialLogin);
}
