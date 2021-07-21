package com.matzipuniv.sinchon.web;


import com.matzipuniv.sinchon.service.ImageService;
import com.matzipuniv.sinchon.web.dto.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;

    @GetMapping("/api/images/{imageNum}")
    public ImageResponseDto searchByNum(@PathVariable Long imageNum){
        return imageService.searchByNum(imageNum);
    }


}
