package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.*;
import com.matzipuniv.sinchon.service.ImageService;

import lombok.Getter;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;

@Getter
public class ReviewListResponseDto {
    private Long reviewNum;
    private Restaurant restaurant;
    private User user;
    private String menuName;
    private String content;
    private Double score;
    private Integer likedCnt;
    private String filePath;
    private String tagFood;
    private String tagMood;


//    ImageService imageService;
//    List<ImageResponseDto> imageResponseDto = imageService.findAllDtoByReview(reviewNum);

    public ReviewListResponseDto(Review entity){
        this.reviewNum = entity.getReviewNum();
        this.restaurant = entity.getRestaurant();
        this.user = entity.getUser();
        this.menuName = entity.getMenuName();
        this.content = entity.getContent();
        this.score = entity.getScore();
        this.likedCnt = entity.getLikedCnt();

        if(!entity.getImage().isEmpty()) {
            this.filePath = entity.getImage().get(0).getFilePath();
        }
        else {
            this.filePath = null;
        }
        this.tagFood = entity.getTagFood();
        this.tagMood = entity.getTagMood();
    }

}
