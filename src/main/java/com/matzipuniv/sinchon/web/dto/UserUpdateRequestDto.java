package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserUpdateRequestDto {
    private String nickname;

    public UserUpdateRequestDto(String nickname){
        this.nickname = nickname;
    }

}
