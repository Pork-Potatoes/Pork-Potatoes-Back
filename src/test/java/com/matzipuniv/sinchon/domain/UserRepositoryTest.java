package com.matzipuniv.sinchon.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanup(){
        userRepository.deleteAll();
    }

    @Test
    public void 유저정보_불러오기(){
        //given
        String nickname = "testname";
        Integer coin = 0;
        String email = "testemail";

        userRepository.save(User.builder()
                .nickname(nickname)
                .coin(coin)
                .email(email)
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getCoin()).isEqualTo(coin);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2021,7,15,0,0,0);
        userRepository.save(User.builder()
                .nickname("testname")
                .coin(0)
                .email("testemail")
                .authenticatedDate(now)
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);

        System.out.print(">>>>>>>>> createdDate="+user.getCreatedDate());

        assertThat(user.getCreatedDate()).isAfter(now);
    }
}