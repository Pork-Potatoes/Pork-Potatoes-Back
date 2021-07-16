package com.matzipuniv.sinchon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempMenuRepository extends JpaRepository<TempMenu, Long> {
    List<TempMenu> findAllByRestaurant(Long restaurant);
    //TempMenu findOneById(Long id);
}
