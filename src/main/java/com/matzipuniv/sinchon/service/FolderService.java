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
        Folder folder = folderRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("해당 폴더가 없습니다. num = "+num));
       if(folder.getDeleteFlag())
           throw new IllegalArgumentException("해당 폴더가 없습니다. num = "+num);

        return new FolderResponseDto(folder);
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> findAll(){
        List<FolderResponseDto> folderResponse = new ArrayList<>();

        for(Folder folder: folderRepository.findAll()){
            if(folder.getDeleteFlag())
                continue;
            folderResponse.add(new FolderResponseDto(folder));
        }

        return folderResponse;
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> findByOrderByPinnedCnt(){
        List<FolderResponseDto> folderResponse = new ArrayList<>();

        for(Folder folder: folderRepository.findByOrderByPinnedCntDesc()){
            if(folder.getDeleteFlag())
                continue;
            folderResponse.add(new FolderResponseDto(folder));
        }

        return folderResponse;
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> findByUser(Long userNum){
        User user = userRepository.findByUserNumAndDeleteFlagFalse(userNum);
        if(user == null)
            throw new IllegalArgumentException("없는 유저입니다. user_num = "+userNum);

        List<FolderResponseDto> folderResponse = new ArrayList<>();

        for(Folder folder: folderRepository.findByUser(user)){
            if(folder.getDeleteFlag())
                continue;
            folderResponse.add(new FolderResponseDto(folder));
        }
        return folderResponse;
    }

    @Transactional
    public String deleteFolder(Long userNum, Long folderNum){
        User user = userRepository.findByUserNumAndDeleteFlagFalse(userNum);
        if(user == null)
            throw new IllegalArgumentException("없는 유저입니다. user_num = "+userNum);

        Folder folder = folderRepository.findById(folderNum)
                .orElseThrow(() -> new IllegalArgumentException("해당 폴더가 없습니다. num = "+folderNum));
        if(folder.getDeleteFlag())
            throw new IllegalArgumentException("해당 폴더가 없습니다. num = "+folderNum);

        if(folder.getUser().equals(user)){
            folder.updateDeleteFlag(true);
            return "Success";
        }
        else{
            return "No permission";
        }
    }

    @Transactional(readOnly = true)
    public AdditionResponseDto getRestaurants(Long folderNum){
        List<RestaurantListResponseDto> restaurants = additionRepository.findByFolderNumAndDeleteFlagFalse(folderNum)
                .stream()
                .map(RestaurantListResponseDto::new)
                .collect(Collectors.toList());

        return AdditionResponseDto.builder()
                .folderNum(folderNum)
                .restaurants(restaurants)
                .build();
    }
}
