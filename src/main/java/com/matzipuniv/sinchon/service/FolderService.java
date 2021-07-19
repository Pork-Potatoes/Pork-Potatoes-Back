package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.*;
import com.matzipuniv.sinchon.web.dto.AdditionResponseDto;
import com.matzipuniv.sinchon.web.dto.FolderResponseDto;
import com.matzipuniv.sinchon.web.dto.RestaurantListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final AdditionRepository additionRepository;

    @Transactional(readOnly = true)
    public FolderResponseDto findById(Long num){
        Folder entity = folderRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("해당 폴더가 없습니다. num = "+num));

        return new FolderResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> findAll(){
        return folderRepository.findAll().stream()
                .map(FolderResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> findByOrderByPinnedCnt(){
        return folderRepository.findByOrderByPinnedCntDesc().stream()
                .map(FolderResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> findByUser(Long userNum){
        User entity = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+userNum));

        return folderRepository.findByUser(entity).stream()
                .map(FolderResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AdditionResponseDto getRestaurants(Long folderNum){
        List<RestaurantListResponseDto> restaurants = additionRepository.findByFolderNum(folderNum).stream()
                .map(RestaurantListResponseDto::new)
                .collect(Collectors.toList());

        return AdditionResponseDto.builder()
                .folderNum(folderNum)
                .restaurants(restaurants)
                .build();
    }
}
