package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Menu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuDto {
    private Long menuNum;
    private Long restaurant;
    private String menuName;

    public MenuDto(Menu menu){
        this.menuNum = menu.getMenuNum();
        this.restaurant = menu.getRestaurant();
        this.menuName = menu.getMenuName();
    }

}
