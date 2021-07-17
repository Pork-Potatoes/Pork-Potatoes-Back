package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.domain.Review;
import com.matzipuniv.sinchon.domain.User;
import lombok.Getter;

@Getter
public class ReviewListResponseDto {
    private Long reviewNum;
    private Restaurant restaurant;
    private User user;
    private String menuName;
    private String content;
    private Double score;
    private Integer likedCnt;
    private Long thumbnailNum;

    public ReviewListResponseDto(Review entity){
        this.reviewNum = entity.getReviewNum();
        this.restaurant = entity.getRestaurant();
        this.user = entity.getUser();
        this.menuName = entity.getMenuName();
        this.content = entity.getContent();
        this.score = entity.getScore();
        this.likedCnt = entity.getLikedCnt();

        if(!entity.getImage().isEmpty()) {
            this.thumbnailNum = entity.getImage().get(0).getImageNum();
        }
        else {
            this.thumbnailNum = 0L;
        }
    }

}
