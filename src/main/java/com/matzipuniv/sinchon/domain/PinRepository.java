package com.matzipuniv.sinchon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {
    List<Pin> findByUser(User user);
}
