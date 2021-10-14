package com.matzipuniv.sinchon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "alarm")
public class Alarm extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmNum;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column
    private Boolean readFlag;

    @Column
    private String message;

    @Column
    private Integer type;


    public void updateReadFlag(){
        this.readFlag = true;
    }


    @Builder
    public Alarm(Long user, String alarmMessage, Integer type){
        this.user = user;
        this.alarmReadFlag = false;
        this.alarmMessage = alarmMessage;
        this.type = type;
    }

}
