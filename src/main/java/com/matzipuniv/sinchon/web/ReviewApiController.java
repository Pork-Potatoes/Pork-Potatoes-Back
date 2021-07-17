package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.domain.Review;
import com.matzipuniv.sinchon.service.ReviewService;
import com.matzipuniv.sinchon.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {

    private final ReviewService reviewService;

    @GetMapping("/api/reviews/{reviewNum}")
    public ReviewResponseDto findByNum(@PathVariable Long reviewNum){
        return reviewService.findByNum(reviewNum);

    }

//    @GetMapping("/api/reviews/sorts/createdDate")
//    public List<Review> finAllReviewSortByDate(){
//        return reviewService.findAllSortByDate();
//    }

//    @GetMapping("api/reviews")
//    public List<Review> findAllByContent(@RequestParam String search){
//        List<Review> reviews = reviewService.findAllByContent(search);
//        reviews.addAll(reviewService.findAllByMenuName(search));
//        return reviews;
//    }
//
//    @GetMapping("api/reviews/menus")
//    public List<Review> findAllByMenu(@RequestParam String menu){
//        return reviewService.findAllByMenuName(menu);
//    }

    @GetMapping("api/reviews/sorts/createdDate")
    public List<Review> findAllReviewsSortByDate(@RequestParam String search){
        return reviewService.findAllReviewsSortByData(search);
    }
}
