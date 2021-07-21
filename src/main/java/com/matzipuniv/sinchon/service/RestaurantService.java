package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.AdditionRepository;
import com.matzipuniv.sinchon.domain.MenuRepository;
import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.domain.RestaurantRepository;
import com.matzipuniv.sinchon.web.dto.RestaurantListResponseDto;
import com.matzipuniv.sinchon.web.dto.RestaurantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

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

    @Transactional(readOnly = true)
    public List<RestaurantListResponseDto> searchRestaurants(String query){
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.addAll(restaurantRepository.findByRestaurantNameContaining(query));

        for(Restaurant restaurant : menuRepository.findByMenuNameContaining(query)){
            if(!restaurants.contains(restaurant)){
                restaurants.add(restaurant);
            }
        }

        restaurants.sort(Comparator.comparing(Restaurant::getAvgScore).reversed());


        return restaurants.stream().map(RestaurantListResponseDto::new)
                .collect(Collectors.toList());
    }

}
