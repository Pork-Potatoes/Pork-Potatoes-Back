package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.Menu;
import com.matzipuniv.sinchon.domain.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository){
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAllMenuByRestaruant(Long restaurant){
        return menuRepository.findAllByRestaurant(restaurant);
    }

}
