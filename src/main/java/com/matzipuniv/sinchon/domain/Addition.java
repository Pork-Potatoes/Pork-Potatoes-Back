package com.matzipuniv.sinchon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="addition")
public class Addition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long additionNum;

    @Column(name = "folder")
    private Long folderNum;

    @ManyToOne
    @JoinColumn(name = "restaurant")
    private Restaurant restaurant;

    @Column
    private Boolean deleteFlag;

    @Builder
    public Addition(Long folderNum, Restaurant restaurant, Boolean deleteFlag){
        this.folderNum = folderNum;
        this.restaurant = restaurant;
        this.deleteFlag = deleteFlag;
    }
}
