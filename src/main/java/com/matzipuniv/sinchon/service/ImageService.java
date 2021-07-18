package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.Image;
import com.matzipuniv.sinchon.domain.ImageRepository;
import com.matzipuniv.sinchon.web.dto.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public List<ImageResponseDto> findAllDtoByReview(Long reviewNum){
        List<Image> imageList = imageRepository.findAllByReviewReviewNum(reviewNum);

        return imageList.stream()
                .map(ImageResponseDto::new)
                .collect(Collectors.toList());
    }
}
