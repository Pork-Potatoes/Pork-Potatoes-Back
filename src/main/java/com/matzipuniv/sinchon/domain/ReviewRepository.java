package com.matzipuniv.sinchon.domain;

import com.matzipuniv.sinchon.web.dto.RestaurantResponseDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT r from Review r where r.content like %:content%")
    List<Review> findAllByContent(String content);

    @Query(value = "SELECT r from Review r where r.menuName like %:menuName%")
    List<Review> findAllByMenu(String menuName);

    List<Review> findAllByRestaurant(Restaurant restaurant);
}
