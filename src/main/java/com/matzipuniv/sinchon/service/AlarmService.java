package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.Alarm;
import com.matzipuniv.sinchon.domain.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public void saveAlarm(Long user, String message, int type) {
        Alarm entity = Alarm.builder().user(user)
                                    .alarmMessage(message)
                                    .type(type)
                                    .build();
        alarmRepository.save(entity);
        System.out.println(entity.getAlarmMessage());
    }
}
