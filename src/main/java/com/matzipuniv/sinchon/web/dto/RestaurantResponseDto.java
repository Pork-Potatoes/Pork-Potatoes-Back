package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantResponseDto {
    private Long restaurantNum;
    private String restaurantName;
    private String address;
    private String phoneNum;
    private String businessHour;
    private String snsAccount;
    private String notice;
    private Double avgScore;

    public RestaurantResponseDto(Restaurant entity){
        this.restaurantNum = entity.getRestaurantNum();
        this.restaurantName = entity.getRestaurantName();
        this.address = entity.getAddress();
        this.phoneNum = entity.getPhoneNum();
        this.businessHour = entity.getBusinessHour();
        this.snsAccount = entity.getSnsAccount();
        this.notice = entity.getNotice();
        this.avgScore = entity.getAvgScore();
    }
}
