package com.matzipuniv.sinchon.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name="user")
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNum;

    @Column
    private String nickname;

    @Column
    private String profileUrl;

    @Column
    private Integer coin;

    @Column
    private String university;

    @Column
    private String email;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column
    private LocalDateTime authenticatedDate;

    @Column
    private String socialLogin;

    @Column
    private Boolean deleteFlag;

    @Builder
    public User(String nickname, String profileUrl, Integer coin, String university, String email, LocalDateTime authenticatedDate, String socialLogin, Boolean deleteFlag){
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.coin = coin;
        this.university = university;
        this.email = email;
        this.authenticatedDate = authenticatedDate;
        this.socialLogin = socialLogin;
        this.deleteFlag = deleteFlag;
    }

    @Builder
    public User(String nickname, String profileUrl, Integer coin, String email, String socialLogin) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.coin = coin;
        this.email = email;
        this.socialLogin = socialLogin;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void updateDeleteFlag() {
        this.deleteFlag = true;
    }

    public void updateUniv(String univ) {
        this.university = univ;
    }

    public void updateAuthenticatedDate(LocalDateTime now) {
        this.authenticatedDate = now;
    }
  
    public void updateCoin(Integer coin){
        this.coin += 100;

    }
}
