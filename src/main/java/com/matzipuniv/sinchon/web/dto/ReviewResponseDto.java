package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Image;
import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.domain.Review;
import com.matzipuniv.sinchon.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
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
    private String tagFood;
    private String tagMood;
    private LocalDateTime createdDate;
    private List<String> filePath;


    public ReviewResponseDto(Review entity, List<String> filePath){
        this.reviewNum = entity.getReviewNum();
        this.restaurant = entity.getRestaurant();
        this.user = entity.getUser();
        this.content = entity.getContent();
        this.score = entity.getScore();
        this.anonymousFlag = entity.getAnonymousFlag();
        this.menuName = entity.getMenuName();
        this.likedCnt = entity.getLikedCnt();
        this.report = entity.getReport();
        this.deleteFlag = entity.getDeleteFlag();
        this.tagFood = entity.getTagFood();
        this.tagMood = entity.getTagMood();
        this.createdDate = entity.getCreatedDate();
        this.filePath = filePath;
    }
    

}
