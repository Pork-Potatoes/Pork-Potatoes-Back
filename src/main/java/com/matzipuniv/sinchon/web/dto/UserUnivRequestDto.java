package com.matzipuniv.sinchon.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserUnivRequestDto {
    private String university;
    private String email;

    public UserUnivRequestDto(String university, String email){
        this.university = university;
        this.email = email;
    }
}
