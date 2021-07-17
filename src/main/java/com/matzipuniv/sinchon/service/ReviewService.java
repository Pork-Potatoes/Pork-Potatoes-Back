package com.matzipuniv.sinchon.service;


import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.domain.RestaurantRepository;
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
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public ReviewResponseDto findByNum(Long num){
        Review review = reviewRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. num = " + num));

        return new ReviewResponseDto(review);
    }

    @Transactional
    public List<Review> findAllSortByDate(){
        List<Review> reviews = reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        return reviews;
    }

    @Transactional
    public List<Review> findAllReviewsSortByData(String search){
        List<Review> reviews = reviewRepository.findAllByContent(search);
        reviews.addAll(reviewRepository.findAllByMenu(search));
        Collections.sort(reviews, new Comparator<Review>() {
            @Override
            public int compare(Review o1, Review o2) {
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            }
        });
        Collections.reverse(reviews);
        return reviews;
    }

//    @Transactional
//    public List<Review> findAllByContent(String search){
//        List<Review> reviews = reviewRepository.findAllByContent(search);
//        return reviews;
//    }
//
//    @Transactional
//    public List<Review> findAllByMenuName(String search){
//        List<Review> reviews = reviewRepository.findAllByMenu(search);
//        return reviews;
//    }
//    @Transactional
//    public List<Review> findAllByRestaurant(String restaurant){
//        List<Review> reviews = reviewRepository.findAllByRestaurant(restaurant);
//        return reviews;
//    }
//
//    @Transactional
//    public List<Review> findReviewsByContent(String search){
//        List<Review> reviews = reviewRepository.findAllByContent(search);
//        return reviews;
//    }
//    @Transactional
//    public List<Review> findAllByQuery(String query){
//        return reviewRepository.findAllByQuery(query);
//    }
//
//    //query = 사용자가 입력한 검색어
//    @Transactional
//    public List<Review> findAllSortByDate(String query){
//        List<Review> reviews = reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
//        return reviews;
//    }

//    @Transactional
//    public List<Review> findAllByQuerySortByDate(String query){
//        Sort sort = sortByDate();
//        List<Review> reviews = reviewRepository.findAllByQuerySortByDate(query, sort);
//
//        return reviews;
//    }
//
//    private Sort sortByDate(){
//        return Sort.by(Sort.Direction.DESC, "createdDate");
//    }
}
