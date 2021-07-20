package com.matzipuniv.sinchon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_num", nullable = false)
    private Long menuNum;

    @ManyToOne(targetEntity = Restaurant.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant")
    private Restaurant restaurant;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Builder
    public Menu(Restaurant restaurant, String menuName){
        this.restaurant = restaurant;
        this.menuName = menuName;
    }
}