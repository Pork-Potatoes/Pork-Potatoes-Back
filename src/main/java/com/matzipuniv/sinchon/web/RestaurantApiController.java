
package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.RestaurantService;
import com.matzipuniv.sinchon.web.dto.RestaurantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestaurantApiController {
    private final RestaurantService restaurantService;

    @GetMapping("/api/restaurants/{restaurantNum}")
    public RestaurantResponseDto findByNum(@PathVariable Long restaurantNum){
        return restaurantService.findByNum(restaurantNum);
    }
}

