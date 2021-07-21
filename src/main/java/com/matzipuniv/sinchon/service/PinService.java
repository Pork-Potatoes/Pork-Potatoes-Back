package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.*;
import com.matzipuniv.sinchon.web.dto.FolderResponseDto;
import com.matzipuniv.sinchon.web.dto.PinResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PinService {
    private final PinRepository pinRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;

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

    @Transactional
    public void addPin(Long userNum, Long folderNum){
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+userNum));
        Folder target = folderRepository.findById(folderNum)
                .orElseThrow(() -> new IllegalArgumentException("없는 리스트입니다. folder_num = "+folderNum));

        //즐겨찾기가 이미 눌려있는 경우 예외처리
        pinRepository.findByUserAndFolder(user, target).ifPresent(none -> {throw new RuntimeException();});

        pinRepository.save(
                Pin.builder()
                    .folder(target)
                    .user(user)
                    .build()
        );

        //pin 개수 변화
        Integer cnt = target.getPinnedCnt();
        target.changePinnedCnt(cnt+1);
    }

    @Transactional
    public void deletePin(Long userNum, Long folderNum){
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+userNum));
        Folder target = folderRepository.findById(folderNum)
                .orElseThrow(() -> new IllegalArgumentException("없는 리스트입니다. folder_num"+folderNum));

        //즐겨찾기가 눌려있지 않던 경우 예외처리
        pinRepository.findByUserAndFolder(user, target).orElseThrow(() -> new RuntimeException());
        Long num = pinRepository.findByUserAndFolder(user, target).get().getPinNum();

        pinRepository.deleteById(num);

        //pin 개수 변화
        Integer cnt = target.getPinnedCnt();
        target.changePinnedCnt(cnt-1);
    }
}
