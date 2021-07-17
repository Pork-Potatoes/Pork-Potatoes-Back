package com.matzipuniv.sinchon.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Review extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNum;

    @ManyToOne
    @JoinColumn(name = "restaurant")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user")
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
    private String tagFood;

    @Column
    private String tagMood;

    @OneToMany(
            mappedBy = "review",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Image> image = new ArrayList<>();



}