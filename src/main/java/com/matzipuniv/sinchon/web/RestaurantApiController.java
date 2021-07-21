package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.RestaurantService;
import com.matzipuniv.sinchon.web.dto.RestaurantListResponseDto;
import com.matzipuniv.sinchon.web.dto.RestaurantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantApiController {
    private final RestaurantService restaurantService;

    @GetMapping("/api/restaurants/{restaurantNum}")
    public RestaurantResponseDto findByNum(@PathVariable Long restaurantNum){
        return restaurantService.findByNum(restaurantNum);
    }

    @GetMapping("/api/restaurants/search")
    public List<RestaurantListResponseDto> searchByRestaurantName(@RequestParam(value="q", required = false, defaultValue = "") String restaurantName){
        return restaurantService.searchByRestaurantName(restaurantName);
    }

    @GetMapping("/api/search")
    public List<RestaurantListResponseDto> searchRestaurants(@RequestParam(value="q", required = false, defaultValue = "") String query){
        return restaurantService.searchRestaurants(query);
    }
}
