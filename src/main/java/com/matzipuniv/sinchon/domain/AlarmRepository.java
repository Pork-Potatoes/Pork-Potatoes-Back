package com.matzipuniv.sinchon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
