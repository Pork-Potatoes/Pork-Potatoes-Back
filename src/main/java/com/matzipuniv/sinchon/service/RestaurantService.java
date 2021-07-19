package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.AdditionRepository;
import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.domain.RestaurantRepository;
import com.matzipuniv.sinchon.web.dto.RestaurantListResponseDto;
import com.matzipuniv.sinchon.web.dto.RestaurantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public RestaurantResponseDto findByNum(Long num){
        Restaurant entity = restaurantRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다. num = "+num));

        return new RestaurantResponseDto(entity);
    }

    @Transactional
    public RestaurantResponseDto findByName(String name){
        Restaurant entity = restaurantRepository.findOneByRestaurantName(name);
        Long num = entity.getRestaurantNum();
        Restaurant returnEntity = restaurantRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다."));
        return new RestaurantResponseDto(returnEntity);
    }

}
