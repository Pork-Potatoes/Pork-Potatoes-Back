package com.matzipuniv.sinchon.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Review extends  BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNum;

    @ManyToOne
    @JoinColumn(name = "restaurant-num")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user-num")
    private User user;

    @Column
    private String content;

    @Column
    private Double score;

    @Column
    private Boolean anonymousFlag;

    @Column
    private String menuName;

    @Column
    private Integer likedCnt;

    @Column
    private Integer report;

    @Column
    private Boolean deleteFlag;

    @Column
    private String tagCousine;

    @Column
    private String tagMood;



}