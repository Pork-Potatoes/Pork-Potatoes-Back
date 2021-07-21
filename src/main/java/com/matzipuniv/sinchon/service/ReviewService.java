package com.matzipuniv.sinchon.service;


import com.matzipuniv.sinchon.domain.Image;
import com.matzipuniv.sinchon.domain.ImageRepository;
import com.matzipuniv.sinchon.domain.Review;
import com.matzipuniv.sinchon.domain.ReviewRepository;
import com.matzipuniv.sinchon.web.dto.ImageResponseDto;
import com.matzipuniv.sinchon.web.dto.ReviewRequestDto;
import com.matzipuniv.sinchon.web.dto.ReviewListResponseDto;
import com.matzipuniv.sinchon.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final ImageRepository imageRepository;
    private final FileHandler1 fileHandler1;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ImageRepository imageRepository){
        this.reviewRepository = reviewRepository;
        this.imageRepository = imageRepository;
        this.fileHandler1 = new FileHandler1(imageRepository);
    }

    @Transactional
    public ReviewResponseDto searchByNum(Long num, List<String> filePath){
        Review entity = reviewRepository.findById(num).
                orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. num = " + num));
        if (entity.getDeleteFlag()==true){
            throw new IllegalArgumentException("해당 리뷰가 삭제되었습니다. num = " + num);
        }
        return new ReviewResponseDto(entity, filePath);
    }

    @Transactional
    public void createReview(
            ReviewRequestDto requestDto,
            List<MultipartFile> files
    ) throws Exception{
        Review review = new Review(
                requestDto.getRestaurant(),
                requestDto.getUser(),
                requestDto.getContent(),
                requestDto.getScore(),
                requestDto.getAnonymousFlag(),
                requestDto.getMenuName(),
                0,
                0,
                false,
                requestDto.getTagFood(),
                requestDto.getTagMood()
        );

        List<Image> imageList = fileHandler1.parseFileInfo(files, review);

        reviewRepository.save(review);

        if(!imageList.isEmpty()){
            for(Image image : imageList){
                imageRepository.save(image);
            }
        }

    }

    @Transactional
    public List<ReviewListResponseDto> findAllReviewsSort(String search, String sort){
        List<Review> reviews = reviewRepository.findAllByContentORMenuNameORRestaurant(search, search, search);
        List<Review> reviewsResponse = new ArrayList<>();

        //삭제 확인
        for(Review review: reviews){
            if(review.getDeleteFlag())
                continue;
            reviewsResponse.add(review);
        }

        //sort 종류에 따라 정렬
        if(sort.equals("-created-date")) {
            Collections.sort(reviewsResponse, new Comparator<Review>() {
                @Override
                public int compare(Review o1, Review o2) {
                    return o1.getCreatedDate().compareTo(o2.getCreatedDate());
                }
            });
            Collections.reverse(reviewsResponse);
        }else if(sort.equals("-score")){
            Collections.sort(reviewsResponse, new Comparator<Review>() {
                @Override
                public int compare(Review o1, Review o2) {
                    return o1.getScore().compareTo(o2.getScore());
                }
            });
            Collections.reverse(reviewsResponse);
        }else if(sort.equals("-liked-cnt")){
            Collections.sort(reviewsResponse, new Comparator<Review>() {
                @Override
                public int compare(Review o1, Review o2) {
                    return o1.getLikedCnt().compareTo(o2.getLikedCnt());
                }
            });
            Collections.reverse(reviewsResponse);
        }

        return reviewsResponse.stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public List<ReviewListResponseDto> todaysLikedReviews(){
        LocalDate todaysDate = LocalDate.now();

        List<Review> reviews = reviewRepository.findByDeleteFlagOrderByLikedCntDesc(false);
        List<Review> responseReviews = new ArrayList<>();
        for (Review review : reviews){
            if (review.getCreatedDate().toLocalDate().equals(todaysDate)){
               responseReviews.add(review);
            }
        }
        return responseReviews.stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReviewListResponseDto> recentReviews() {
        List<Review> reviews = reviewRepository.findByDeleteFlagOrderByCreatedDateDesc(false);

        return reviews.stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReviewListResponseDto> restaurantReviews(Long restaurantNum, String sort) {
        List<Review> reviews = new ArrayList<>();

        if(sort.equals("-created-date")){
            reviews = reviewRepository.findByDeleteFlagAndRestaurantRestaurantNumOrderByCreatedDateDesc(false, restaurantNum);
        }

        return reviews.stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ReviewListResponseDto> myReviews(Long userNum, String sort) {
        List<Review> reviews = new ArrayList<>();

        if(sort.equals("-created-date")){
            reviews = reviewRepository.findByDeleteFlagAndUserUserNumOrderByCreatedDateDesc(false, userNum);
        }

        return  reviews.stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    public String delete(Long num) {
        Review entity = reviewRepository.findById(num)
                .orElseThrow(()->new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. num = " + num));
        entity.setDeleteFlag(true);
        reviewRepository.save(entity);
        return "Success";
    }
}
