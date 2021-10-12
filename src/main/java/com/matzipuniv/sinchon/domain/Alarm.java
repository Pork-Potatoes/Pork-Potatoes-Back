package com.matzipuniv.sinchon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="alarm")
public class Alarm  extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmNum;

    @Column
    private Long user;

    @Column
    private Boolean alarmReadFlag;

    @Column
    private String alarmMessage;

    @Column
    private Integer type;

    @Builder
    public Alarm(Long user, String alarmMessage, Integer type){
        this.user = user;
        this.alarmReadFlag = false;
        this.alarmMessage = alarmMessage;
        this.type = type;
    }

    public void updateReadFlag() {
        this.alarmReadFlag = true;
    }
}
