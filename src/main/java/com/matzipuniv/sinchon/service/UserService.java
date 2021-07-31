package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.*;
import com.matzipuniv.sinchon.domain.User;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import com.matzipuniv.sinchon.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PinRepository pinRepository;
    private final S3UploaderProfile s3UploaderProfile;

    @Transactional
    public List<UserResponseDto> findAll() {
        List<User> entities = userRepository.findAllByDeleteFlagFalse();
        if(entities==null) {
            System.out.println("유저가 없습니다");
        }
        List<UserResponseDto> responseDtoList = new ArrayList<>();
        for(User entity : entities){
            responseDtoList.add(new UserResponseDto(entity));
        }
        return responseDtoList;
    }

    @Transactional
    public UserResponseDto findByNum(Long num){
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            System.out.println("없는 유저입니다. user_num = "+num);
        }
        if(entity.getAuthenticatedDate()!=null) {
            long dayGone = compareDay(LocalDateTime.now(), entity.getAuthenticatedDate());
            if(dayGone > 365) {
                entity.updateUniv(null);
            }
        }
        return new UserResponseDto(entity);
    }

    @Transactional
    public String deleteUser(Long num) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }
        List<Pin> pins = pinRepository.findByUser(entity);
        try {
            entity.updateDeleteFlag();
            for(Pin pin : pins) {
                pinRepository.delete(pin);
            }
            return "user"+num+" deleted";
        }
        catch(Exception e) {
            return "error occured";
        }
    }

    @Transactional
    public String updateNickname(Long num, UserUpdateRequestDto nicknameDto) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }
        String nickname = nicknameDto.getNickname();
        if(!(nickname == null)){
            entity.updateNickname(nickname);
            return "modified";
        } else {
            return "nickname is null";
        }
    }

    @Transactional
    public String updateProfile(Long num, MultipartFile uploadFile) {
        String newProfile;
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity == null) {
            return "없는 유저입니다. user_num = "+num;
        }
        try{
            String currProfile = entity.getProfileUrl();
            newProfile = s3UploaderProfile.upload(currProfile, uploadFile);
            //제대로 업로드가 된 경우에만 기존프로필사진 s3에서 삭제
            if(newProfile!=null) {
                entity.updateProfileUrl("http://" + s3UploaderProfile.CLOUD_FRONT_DOMAIN_NAME + "/" + newProfile);
                s3UploaderProfile.delete(currProfile);
            } else { //png나 jpeg가 아니라서 문제가 생긴경우
                return "file type is not proper or is corrupted";
            }
        } catch (Exception e) { //그냥 업로드하다가 문제가 생긴 경우
            System.out.println("File exception");
            return "error occurred during upload" + e.getMessage();
        }
        return "modified";
    }

    @Transactional
    public String deleteProfile(Long num) {
        //미완성
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }
        String currUrl = entity.getProfileUrl();
        if(!currUrl.equals("http://d18omhl2ssqffk.cloudfront.net/default-20215529215533.png")) {
            try {
                s3UploaderProfile.delete(currUrl);
            } catch(Exception e) {
                return "delete file error" + e.getMessage();
            }
        }
        entity.updateProfileUrl("http://d18omhl2ssqffk.cloudfront.net/default-20215529215533.png");
        return "deleted";
    }

    @Transactional
    public String findUniv(Long num) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }
        String authenticate = entity.getUniversity();
        return ((authenticate==null) ? "not authenciated" : authenticate);
    }

    @Transactional
    public String setUniv(Long num, String univ) {
        LocalDateTime now = LocalDateTime.now();
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }
        return ((!(univ==null) || !univ.equals("null")) ? "success" : "univ is null");
    }

    public long compareDay(LocalDateTime now, LocalDateTime auth_date) {
        LocalDateTime now2 = now.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime auth_date2 = auth_date.truncatedTo(ChronoUnit.DAYS);
        return ChronoUnit.DAYS.between(auth_date2, now2);
    }
}
