package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.ImageService;
import com.matzipuniv.sinchon.service.ReviewService;
import com.matzipuniv.sinchon.web.dto.ImageResponseDto;
import com.matzipuniv.sinchon.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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



}
