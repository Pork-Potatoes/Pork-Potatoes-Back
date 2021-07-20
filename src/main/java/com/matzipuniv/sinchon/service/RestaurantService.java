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
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<RestaurantListResponseDto> searchByRestaurantName(String restaurantName){
        return restaurantRepository.findByRestaurantNameContaining(restaurantName).stream()
                .map(RestaurantListResponseDto::new)
                .collect(Collectors.toList());
    }

}
