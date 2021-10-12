package com.matzipuniv.sinchon.config;

import com.matzipuniv.sinchon.domain.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
//엔터티 클래스 자체를 직렬화하면 해당 엔터티와 관계를 가지고 있는 다른 엔터티까지도 직렬화 대상에 포함되므로
//직렬화 기능을 가진 세션 dto를 추가로 생성
public class SessionUser implements Serializable {
    private Long userNum;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.userNum = user.getUserNum();
        this.name = user.getNickname();
        this.email = user.getEmail();
        this.picture = user.getProfileUrl();
    }
}
