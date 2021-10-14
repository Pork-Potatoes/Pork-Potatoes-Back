package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.Alarm;
import com.matzipuniv.sinchon.domain.AlarmRepository;
import com.matzipuniv.sinchon.domain.UserRepository;
import com.matzipuniv.sinchon.web.dto.AlarmDto;
import com.matzipuniv.sinchon.web.dto.AlarmListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlarmService {
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;


    @Transactional
    public List<AlarmListDto> myAlarm(Long userNum) {
        List<Alarm> alarms = new ArrayList<>();
        alarms = alarmRepository.findByUser_UserNum(userNum);

        return alarms.stream()
                .map(AlarmListDto::new)
                .collect(Collectors.toList());
    }

    public String readAlarm(Long alarmNum) {
        Alarm entity = alarmRepository.findById(alarmNum)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림이 없습니다. alarm = " + alarmNum));
        entity.updateReadFlag();
        alarmRepository.save(entity);
        return "success";
    }

    public void saveAlarm(Long user, String message, int type) {
        Alarm entity = Alarm.builder().user(user)
                                    .alarmMessage(message)
                                    .type(type)
                                    .build();
        alarmRepository.save(entity);
        System.out.println(entity.getAlarmMessage());

    }
}
