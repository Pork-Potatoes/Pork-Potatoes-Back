package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.domain.Menu;
import com.matzipuniv.sinchon.domain.MenuRepository;
import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.domain.RestaurantRepository;
import com.matzipuniv.sinchon.service.MenuService;
import com.matzipuniv.sinchon.web.dto.MenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService){
        this.menuService = menuService;
    }

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/api/restaurants/{restaurant_num}/menus")
    public List<MenuDto> getAllMenuByRestaurant(@PathVariable Long restaurant_num){
        Restaurant restaurant = restaurantRepository.findById(restaurant_num).get();
        return menuService.getAllMenuByRestaruant(restaurant);
    }
}