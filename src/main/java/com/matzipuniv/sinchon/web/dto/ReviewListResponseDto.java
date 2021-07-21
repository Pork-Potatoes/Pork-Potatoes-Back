package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.*;
import com.matzipuniv.sinchon.service.ImageService;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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

    ImageService imageService;
    List<ImageResponseDto> imageResponseDto = imageService.findAllDtoByReview(reviewNum);

    public ReviewListResponseDto(Review entity){
        this.reviewNum = entity.getReviewNum();
        this.restaurant = entity.getRestaurant();
        this.user = entity.getUser();
        this.menuName = entity.getMenuName();
        this.content = entity.getContent();
        this.score = entity.getScore();
        this.likedCnt = entity.getLikedCnt();

        if(!imageResponseDto.isEmpty()) {
            this.thumbnailNum = imageResponseDto.get(0).getImageNum();
        }
        else {
            this.thumbnailNum = 0L;
        }
    }

}
