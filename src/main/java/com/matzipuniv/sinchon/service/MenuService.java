
package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.Menu;
import com.matzipuniv.sinchon.domain.MenuRepository;
import com.matzipuniv.sinchon.domain.Restaurant;
import com.matzipuniv.sinchon.web.dto.MenuDto;
import com.matzipuniv.sinchon.web.dto.ReviewListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository){
        this.menuRepository = menuRepository;
    }

    @Transactional(readOnly = true)
    public List<MenuDto> getAllMenuByRestaruant(Restaurant restaurant){
        List<Menu> menu = menuRepository.findAllByRestaurant(restaurant);

        return menu.stream()
                .map(MenuDto::new)
                .collect(Collectors.toList());
    }

}