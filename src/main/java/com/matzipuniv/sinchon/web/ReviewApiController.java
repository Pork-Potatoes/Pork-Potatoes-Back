package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.ReviewService;
import com.matzipuniv.sinchon.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {

    private final ReviewService reviewService;

    @GetMapping("/api/reviews/{reviewNum}")
    public ReviewResponseDto findByNum(@PathVariable Long reviewNum){
        return reviewService.findByNum(reviewNum);

    }

}
