package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.domain.Review;
import com.matzipuniv.sinchon.domain.User;
import lombok.Getter;


@Getter
public class ReviewResponseDto {
    private Long reviewNum;
    private Restaurant restaurant;
    private User user;
    private String content;
    private Double score;
    private Boolean anonymousFlag;
    private String menuName;
    private Integer likedCnt;
    private Integer report;
    private Boolean deleteFlag;
    private String tagCousine;
    private String tagMood;

    public ReviewResponseDto(Review review){
        this.reviewNum = review.getReviewNum();
        this.restaurant = review.getRestaurant();
        this.user = review.getUser();
        this.content = review.getContent();
        this.score = review.getScore();
        this.anonymousFlag = review.getAnonymousFlag();
        this.menuName = review.getMenuName();
        this.likedCnt = review.getLikedCnt();
        this.report = review.getReport();
        this.deleteFlag = review.getDeleteFlag();
        this.tagCousine = review.getTagCousine();
        this.tagMood = review.getTagMood();
    }



}
