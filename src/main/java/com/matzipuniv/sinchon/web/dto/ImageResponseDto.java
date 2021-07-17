package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Image;
import com.matzipuniv.sinchon.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageResponseDto {
    private Long imageNum;
    private Review review;
    private String originalFileName;
    private String filePath;
    private Long fileSize;


    @Builder
    public ImageResponseDto(Long imageNum, Review review, String originalFileName, String filePath, Long fileSize, Boolean deleteFlag){
        this.imageNum = imageNum;
        this.review = review;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;

    }

    public ImageResponseDto(Image image) {
        this.imageNum = image.getImageNum();
        this.review = image.getReview();
        this.originalFileName = image.getOriginalFileName();
        this.filePath = image.getFilePath();
        this.fileSize = image.getFileSize();
    }
}
