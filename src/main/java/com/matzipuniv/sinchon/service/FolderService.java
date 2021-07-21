package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.*;
import com.matzipuniv.sinchon.web.dto.AdditionResponseDto;
import com.matzipuniv.sinchon.web.dto.FolderResponseDto;
import com.matzipuniv.sinchon.web.dto.FolderSaveRequestDto;
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
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Long saveFolder(FolderSaveRequestDto requestDto){
        return folderRepository.save(requestDto.toEntity()).getFolderNum();
    }

    @Transactional(readOnly = true)
    public FolderResponseDto findById(Long num){
        Folder folder = folderRepository.findByFolderNumAndDeleteFlagFalse(num);
        if(folder == null){
            throw new IllegalArgumentException("없는 폴더입니다. folder_num = "+num);
        }

        return new FolderResponseDto(folder);
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> findAll(){
        List<FolderResponseDto> folderResponse = new ArrayList<>();

        for(Folder folder: folderRepository.findAll()){
            if(folder.getDeleteFlag()){
                continue;
            }
            folderResponse.add(new FolderResponseDto(folder));
        }

        return folderResponse;
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> findByOrderByPinnedCnt(){
        List<FolderResponseDto> folderResponse = new ArrayList<>();

        for(Folder folder: folderRepository.findByOrderByPinnedCntDesc()){
            if(folder.getDeleteFlag()){
                continue;
            }
            folderResponse.add(new FolderResponseDto(folder));
        }

        return folderResponse;
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> findByUser(Long userNum){
        User user = userRepository.findByUserNumAndDeleteFlagFalse(userNum);
        if(user == null){
            throw new IllegalArgumentException("없는 유저입니다. user_num = "+userNum);
        }

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
        if(user == null){
            throw new IllegalArgumentException("없는 유저입니다. user_num = "+userNum);
        }

        Folder folder = folderRepository.findByFolderNumAndDeleteFlagFalse(folderNum);
        if(folder == null){
            throw new IllegalArgumentException("없는 폴더입니다. folder_num = "+folderNum);
        }

        if(folder.getUser().equals(user)){
            folder.updateDeleteFlag(true);
            return "Success";
        }
        else{
            return "No permission";
        }
    }

    @Transactional
    public String saveRestaurant(Long restaurantNum, Long folderNum){
        Restaurant restaurant = restaurantRepository.findById(restaurantNum)
                .orElseThrow(()-> new IllegalArgumentException("해당 가게가 없습니다."));

        Addition addition = additionRepository.findByFolderNumAndRestaurant(folderNum,restaurant);

        if(addition==null){
            additionRepository.save(Addition.builder()
                    .restaurant(restaurant)
                    .folderNum(folderNum)
                    .deleteFlag(false)
                    .build());
        }
        else if(addition.getDeleteFlag()){
            addition.updateDeleteFlag(false);
        }
        else{
            return "Fail";
        }
        return "Success";
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

    @Transactional
    public String deleteRestaurant(Long userNum, Long folderNum, Long restaurantNum){
        User user = userRepository.findByUserNumAndDeleteFlagFalse(userNum);
        if(user == null){
            throw new IllegalArgumentException("없는 유저입니다. user_num = "+userNum);
        }

        Folder folder = folderRepository.findByFolderNumAndDeleteFlagFalse(folderNum);
        if(folder == null){
            throw new IllegalArgumentException("없는 폴더입니다. folder_num = "+folderNum);
        }

        Restaurant restaurant = restaurantRepository.findById(restaurantNum)
                .orElseThrow(()-> new IllegalArgumentException("해당 가게가 없습니다."));

        Addition addition = additionRepository.findByFolderNumAndRestaurant(folderNum, restaurant);

        if(addition == null || addition.getDeleteFlag()){
            throw new IllegalArgumentException("해당 가게는 해당 폴더에 속해있지 않습니다. folder_num = "+ folderNum+ ", restaurant_num = "+restaurantNum);
        }
        else if(folder.getUser().equals(user)) {
            addition.updateDeleteFlag(true);
            return "Success";
        }
        else{
            return "No permission";
        }
    }
}
