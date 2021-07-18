package com.matzipuniv.sinchon.service;


import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.domain.RestaurantRepository;
import com.matzipuniv.sinchon.domain.ImageRepository;
import com.matzipuniv.sinchon.domain.Review;
import com.matzipuniv.sinchon.domain.ReviewRepository;
import com.matzipuniv.sinchon.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    private final RestaurantService restaurantService;
    private final ImageRepository imageRepository;
    private final FileHandler1 fileHandler1;

    @Transactional
    public ReviewResponseDto searchByNum(Long num, List<String> filePath){
        Review entity = reviewRepository.findById(num).orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. num = " + num));

        return new ReviewResponseDto(entity, filePath);
    }

    @Transactional
    public List<Review> findAllSortByDate(){
        List<Review> reviews = reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        return reviews;
    }

    @Transactional
    public List<ReviewResponseDto> findAllReviewsSort(String search, String sort){
        List<Review> reviews = reviewRepository.findAllByContent(search);
        reviews.addAll(reviewRepository.findAllByMenu(search));
        reviews.addAll(reviewRepository.findAllByRestaurant(restaurantRepository.findOneByRestaurantName(search)));
        if(sort.equals("-created-date")) {
            Collections.sort(reviews, new Comparator<Review>() {
                @Override
                public int compare(Review o1, Review o2) {
                    return o1.getCreatedDate().compareTo(o2.getCreatedDate());
                }
            });
            Collections.reverse(reviews);
        }else if(sort.equals("-score")){
            Collections.sort(reviews, new Comparator<Review>() {
                @Override
                public int compare(Review o1, Review o2) {
                    return o1.getScore().compareTo(o2.getScore());
                }
            });
            Collections.reverse(reviews);
        }else if(sort.equals("-liked-cnt")){
            Collections.sort(reviews, new Comparator<Review>() {
                @Override
                public int compare(Review o1, Review o2) {
                    return o1.getLikedCnt().compareTo(o2.getLikedCnt());
                }
            });
            Collections.reverse(reviews);
        }

        return reviews.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());

    }

}
