package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Image;
import com.matzipuniv.sinchon.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ImageResponseDto {
    private String filePath;


    public ImageResponseDto(Image image) {
        this.filePath = image.getFilePath();
    }
}
