package com.matzipuniv.sinchon.web;


import com.matzipuniv.sinchon.service.AlarmService;
import com.matzipuniv.sinchon.web.dto.AlarmDto;
import com.matzipuniv.sinchon.web.dto.AlarmListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class AlarmApiController {
    private final AlarmService alarmService;

    @GetMapping("/api/users/{userNum}/alarms")
    public List<AlarmListDto> myAlarm(@PathVariable Long userNum){
        return alarmService.myAlarm(userNum);
    }

    @PatchMapping("/api/alarms/{alarmNum}/readFlag")
    public String readAlarm(@PathVariable Long alarmNum){
        return alarmService.readAlarm(alarmNum);
    }
}
