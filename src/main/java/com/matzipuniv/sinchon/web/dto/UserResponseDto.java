package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long userNum;
    private String nickname;
    private String profileUrl;
    private Integer coin;
    private String university;
    private String email;
    private LocalDateTime authenticatedDate;
    private Boolean deleteFlag;

    public UserResponseDto(User entity) {
        this.userNum = entity.getUserNum();
        this.nickname = entity.getNickname();
        this.profileUrl = entity.getProfileUrl();
        this.coin = entity.getCoin();
        this.university = entity.getUniversity();
        this.email = entity.getEmail();
        this.authenticatedDate = entity.getAuthenticatedDate();
        this.deleteFlag = entity.getDeleteFlag();
    }
}
