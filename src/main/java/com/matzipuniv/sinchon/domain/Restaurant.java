package com.matzipuniv.sinchon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantNum;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column
    private String address;

    @Column
    private String phoneNum;

    @Column
    private String businessHour;

    @Column
    private String snsAccount;

    @Column
    private String notice;

    @Column
    private Double avgScore;

    @Builder
    public Restaurant(String restaurantName, String address, String phoneNum, String businessHour, String snsAccount, String notice, Double avgScore){
        this.restaurantName = restaurantName;
        this.address = address;
        this.phoneNum = phoneNum;
        this.businessHour = businessHour;
        this.snsAccount = snsAccount;
        this.notice = notice;
        this.avgScore = avgScore;
    }

    public void updateAvgScore(Integer currentReviewCount, Double currentScore){
        Double currentAvgScore = this.avgScore;
        Double changedAvgScore = (currentAvgScore * currentReviewCount + currentScore)/ (currentReviewCount + 1);
        this.avgScore = changedAvgScore;
    }


}
