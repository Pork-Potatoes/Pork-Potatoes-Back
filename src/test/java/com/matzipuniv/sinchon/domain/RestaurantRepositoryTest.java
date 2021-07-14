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
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @AfterEach
    public void cleanup(){
        restaurantRepository.deleteAll();
    }

    @Test
    public void 가게정보_불러오기(){
        //given
        String restaurantName = "testname";
        String address = "testaddress";
        Double avgScore = 0.0;

        restaurantRepository.save(Restaurant.builder()
                .restaurantName(restaurantName)
                .address(address)
                .avgScore(avgScore)
                .build());

        //when
        List<Restaurant> restaurantList = restaurantRepository.findAll();

        //then
        Restaurant restaurant = restaurantList.get(0);
        assertThat(restaurant.getRestaurantName()).isEqualTo(restaurantName);
        assertThat(restaurant.getAddress()).isEqualTo(address);
        assertThat(restaurant.getAvgScore()).isEqualTo(avgScore);
    }

}