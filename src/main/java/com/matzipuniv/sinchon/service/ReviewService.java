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
import java.time.LocalDateTime;
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

//    @Transactional
//    public Review addReview(
//            Review review,
//            List<MultipartFile> files
//    )throws Exception{
//        List<Image> list = fileHandler1.parseFileInfo(files);
//
//        if(list.isEmpty()){
//            // 파일 없을 때 처리
//        }
//        else{
//            List<Image> imageList = new ArrayList<>();
//            for(Image image: list){
//                imageList.add(imageRepository.save(image));
//            }
//            review.setImage(imageList);
//        }
//
//        return reviewRepository.save(review);
//    }

    @Transactional
    public ReviewResponseDto searchByNum(Long num, List<String> filePath){
        Review entity = reviewRepository.findById(num).orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. num = " + num));
        if (entity.getDeleteFlag()==true){
            throw new IllegalArgumentException("해당 리뷰가 삭제되었습니다. num = " + num);
        }
        return new ReviewResponseDto(entity, filePath);
    }

    @Transactional
    public Long createReview(
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
//        if(!imageList.isEmpty()){
//            for(Image image : imageList){
//                ImageResponseDto imageResponseDto = new ImageResponseDto(image);
//            }
//        }
//        if(!imageList.isEmpty()){
//            for (Image image : imageList){
//                review.addImage(imageRepository.save(image));
//            }
//        }
//        if(!imageList.isEmpty()){
//            for(Image image : imageList){
//                imageRepository.save(image);
//            }
//        }
//
        Long num = reviewRepository.save(review).getReviewNum();

        if(!imageList.isEmpty()){
            for(Image image : imageList){
                imageRepository.save(image);
            }
        }

        return num;
    }

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


}
