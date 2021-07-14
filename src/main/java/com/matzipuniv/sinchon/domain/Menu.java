package com.matzipuniv.sinchon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_num", nullable = false)
    private Long menuNum;

    @Column(name = "restaurant_num", nullable = false)
    private Long restaurant;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Builder
    public Menu(Long restaurant, String menuName){
        this.restaurant = restaurant;
        this.menuName = menuName;
    }
}
