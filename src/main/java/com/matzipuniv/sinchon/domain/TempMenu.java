package com.matzipuniv.sinchon.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "temp_menu")
public class TempMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temp_menu_num", nullable = false)
    private Long tempMenuNum;

    @Column(name = "restaurant", nullable = false)
    private Long restaurant;

    @Column(name = "temp_menu_name", nullable = false)
    private String tempMenuName;

    @Builder
    public TempMenu(Long restaurant, String tempMenuName){
        this.restaurant = restaurant;
        this.tempMenuName = tempMenuName;
    }

}
