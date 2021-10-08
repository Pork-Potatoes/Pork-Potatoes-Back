package com.matzipuniv.sinchon.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT r from Review r where (r.content like %:content% " +
            "or r.menuName like %:menuName% " +
            "or r.restaurant.restaurantName like %:restaurantName%)")
    List<Review> findAllByContentORMenuNameORRestaurant(@Param("content")String content, @Param("menuName") String menuName,
                                                        @Param("restaurantName") String restaurantName);

    List<Review> findByDeleteFlagOrderByLikedCntDesc(Boolean deleteFlag);

    List<Review> findByDeleteFlagOrderByCreatedDateDesc(Boolean deleteFlag);

    List<Review> findByDeleteFlagAndRestaurantRestaurantNumOrderByCreatedDateDesc(Boolean deleteFlag, Long restaurantNum);

    List<Review> findByDeleteFlagAndUserUserNumOrderByCreatedDateDesc(Boolean deleteFlag, Long userNum);

    Integer countByRestaurant(Restaurant restaurant);

    Review findReviewByReviewNum(Long reviewNum);
}
