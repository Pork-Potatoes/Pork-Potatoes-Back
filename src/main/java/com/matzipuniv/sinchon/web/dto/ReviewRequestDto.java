package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.domain.Review;
import com.matzipuniv.sinchon.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    private Restaurant restaurant;
    private User user;
    private String content;
    private Double score;
    private Boolean anonymousFlag;
    private String menuName;
    private String tagFood;
    private String tagMood;


    @Builder
    public ReviewRequestDto(Review entity){
        this.restaurant = entity.getRestaurant();
        this.user = entity.getUser();
        this.content = entity.getContent();
        this.score = entity.getScore();
        this.anonymousFlag = entity.getAnonymousFlag();
        this.menuName = entity.getMenuName();
        this.tagFood = entity.getTagFood();
        this.tagMood = entity.getTagMood();
    }



//    public Review toEntity(){
//        return Review.builder()
//                .restaurant(restaurant)
//                .user(user)
//                .content(content)
//                .score(score)
//                .anonymousFlag(anonymousFlag)
//                .menuName(menuName)
//                .tagFood(tagFood)
//                .tagMood(tagMood)
//                .build();
//    }
}


