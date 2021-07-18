package com.matzipuniv.sinchon.service;


import com.matzipuniv.sinchon.domain.ImageRepository;
import com.matzipuniv.sinchon.domain.Review;
import com.matzipuniv.sinchon.domain.ReviewRepository;
import com.matzipuniv.sinchon.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final FileHandler1 fileHandler1;

    @Transactional
    public ReviewResponseDto searchByNum(Long num, List<String> filePath){
        Review entity = reviewRepository.findById(num).orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. num = " + num));

        return new ReviewResponseDto(entity, filePath);
    }
}
