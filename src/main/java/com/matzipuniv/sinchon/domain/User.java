package com.matzipuniv.sinchon.domain;

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

    @Column
    private LocalDateTime authenticatedDate;

    @Column
    private Boolean deleteFlag;

    @Builder
    public User(String nickname, String profileUrl, Integer coin, String university, String email, LocalDateTime authenticatedDate, Boolean deleteFlag){
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.coin = coin;
        this.university = university;
        this.email = email;
        this.authenticatedDate = authenticatedDate;
        this.deleteFlag = deleteFlag;
    }

    @Builder
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    @Builder
    public void updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Builder
    public void updateDeleteFlag() {
        this.deleteFlag = true;
    }

    @Builder
    public void updateUniv(String univ) {
        this.university = univ;
    }
}
