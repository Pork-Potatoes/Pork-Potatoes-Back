package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.*;
import com.matzipuniv.sinchon.web.dto.FolderResponseDto;
import com.matzipuniv.sinchon.web.dto.PinResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PinService {
    private final PinRepository pinRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<PinResponseDto> findByUser(Long userNum) {
        User entity = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+userNum));

        return pinRepository.findByUser(entity).stream()
                .map(PinResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PinResponseDto> findByUserByCreatedDate(Long userNum) {
        User entity = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+userNum));

        List<Pin> pins = pinRepository.findByUser(entity).stream().collect(Collectors.toList());

        Collections.sort(pins, new Comparator<Pin>() {
            @Override
            public int compare(Pin pin1, Pin pin2) {
                return pin1.getCreatedDate().compareTo(pin2.getCreatedDate());
            }
        });
        Collections.reverse(pins);

        return pins.stream()
                .map(PinResponseDto::new)
                .collect(Collectors.toList());

    }
}
