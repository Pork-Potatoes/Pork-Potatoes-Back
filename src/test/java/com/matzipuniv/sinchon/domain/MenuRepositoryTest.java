package com.matzipuniv.sinchon.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MenuRepositoryTest {

    @Autowired
    MenuRepository menuRepository;

    @AfterEach
    public void cleanup(){
        menuRepository.deleteAll();
    }

    @Test
    public void 메뉴_불러오기(){
        //given
//        Restaurant restaurant = ;
//        String menuName = "testMenu";
//
//        menuRepository.save(Menu.builder()
//                .restaurant(restaurant)
//                .menuName(menuName)
//                .build());
//
//        //when
//        List<Menu> menuList = menuRepository.findAllByRestaurant(restaurant_num);
//
//        //then
//        Menu menu = menuList.get(0);
//        assertThat(menu.getMenuName()).isEqualTo(menuName);
//        assertThat(menu.getRestaurant()).isEqualTo(restaurant_num);
    }

}
