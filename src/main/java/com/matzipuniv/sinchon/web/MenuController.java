package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.domain.Menu;
import com.matzipuniv.sinchon.service.MenuService;
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

    @GetMapping("/api/restaurants/{restaurant_num}/menus")
    public List<Menu> getAllMenuByRestaurant(@PathVariable Long restaurant_num){
        return menuService.getAllMenuByRestaruant(restaurant_num);
    }
}
