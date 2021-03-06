package com.matzipuniv.sinchon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByRestaurant(Restaurant restaurant);

    @Query("select m.restaurant from Menu m where m.menuName like %:menuName%")
    List<Restaurant> findByMenuNameContaining(String menuName);
}