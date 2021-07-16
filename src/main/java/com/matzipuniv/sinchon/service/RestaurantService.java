package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.domain.RestaurantRepository;
import com.matzipuniv.sinchon.web.dto.RestaurantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantResponseDto findByNum(Long num){
        Restaurant entity = restaurantRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다. num = "+num));

        return new RestaurantResponseDto(entity);
    }
}