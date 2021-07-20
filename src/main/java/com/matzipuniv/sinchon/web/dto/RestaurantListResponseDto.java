package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantListResponseDto {
    private Long restaurantNum;
    private String restaurantName;
    private String address;
    private Double avgScore;

    public RestaurantListResponseDto(Restaurant entity){
        this.restaurantNum = entity.getRestaurantNum();
        this.restaurantName = entity.getRestaurantName();
        this.address = entity.getAddress();
        this.avgScore = entity.getAvgScore();
    }
}
