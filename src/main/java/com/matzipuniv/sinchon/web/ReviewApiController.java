package com.matzipuniv.sinchon.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.matzipuniv.sinchon.domain.Image;
import com.matzipuniv.sinchon.domain.Review;
import com.matzipuniv.sinchon.service.ImageService;
import com.matzipuniv.sinchon.service.ReviewService;
import com.matzipuniv.sinchon.web.dto.ImageResponseDto;
import com.matzipuniv.sinchon.web.dto.ReviewRequestDto;
import com.matzipuniv.sinchon.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.matzipuniv.sinchon.web.dto.ReviewListResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController

public class ReviewApiController {
    private final ReviewService reviewService;
    private final ImageService imageService;

    @GetMapping("/api/reviews/{reviewNum}")
    public ReviewResponseDto searchByNum(@PathVariable Long reviewNum){
        List<ImageResponseDto> imageResponseDtoList = imageService.findAllDtoByReview(reviewNum);
        //게시글 num으로 해당 게시글 첨부파일 전체 조회
        List<String> filePath = new ArrayList<>();
        //게시글 첨부파일 num 담을 List 객체 생성
        for(ImageResponseDto imageResponseDto : imageResponseDtoList)
            filePath.add(imageResponseDto.getFilePath()); // 각 첨부파일 num을 imageNum에 추가
        return reviewService.searchByNum(reviewNum, filePath); //게시글 num과 첨부파일 num 목록(imageNum) 전달받아 결과 반환
    }

    @PostMapping("/api/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public String createReview(
            @RequestParam(value = "image", required = false) List<MultipartFile> files,
            @RequestParam(value = "requestDto") String requestDtoString
    ) throws Exception{
        ReviewRequestDto requestDto = new ObjectMapper().readValue(requestDtoString, ReviewRequestDto.class);
        reviewService.createReview(requestDto, files);
        return "Success";
    }


    @GetMapping("/api/reviews")
    public List<ReviewListResponseDto> findAllReviewsSortByDate(@RequestParam String query, String sort){
        return reviewService.findAllReviewsSort(query, sort);
    }

    @GetMapping("/api/reviews/thisWeek")
    public List<ReviewListResponseDto> thisWeeksLikedReviews(){
        return reviewService.thisWeeksLikedReviews();
    }

    @GetMapping("/api/reviews/recent")
    public List<ReviewListResponseDto> recentReviews(){
        return reviewService.recentReviews();
    }

    @GetMapping("/api/restaurants/{restaurantNum}/reviews")
    public List<ReviewListResponseDto> restaurantReviews(@PathVariable Long restaurantNum, @RequestParam String sort){
        return reviewService.restaurantReviews(restaurantNum, sort);
    }

    @GetMapping("/api/users/{userNum}/reviews")
    public List<ReviewListResponseDto> myReviews(@PathVariable Long userNum, @RequestParam String sort){
        return reviewService.myReviews(userNum, sort);
    }

    @DeleteMapping("/api/reviews/{reviewNum}")
    public String delete(@PathVariable Long reviewNum){
        return reviewService.delete(reviewNum);
    }

    @PatchMapping("/api/reviews/{reviewNum}/{userNum}")
    public String like(@PathVariable Long reviewNum, @PathVariable Long userNum){
        return reviewService.like(reviewNum, userNum);
    }
}
