package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Alarm;
import com.matzipuniv.sinchon.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlarmListDto {
    private Long alarmNum;
    private User user;
    private Boolean readFlag;
    private String message;
    private Integer type;
    private LocalDateTime createdDate;

    public AlarmListDto(Alarm entity) {
        this.alarmNum = entity.getAlarmNum();
        this.user = entity.getUser();
        this.readFlag = entity.getReadFlag();
        this.message = entity.getMessage();
        this.type = entity.getType();
        this.createdDate = entity.getCreatedDate();
    }
}