package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.*;
import com.matzipuniv.sinchon.domain.User;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PinRepository pinRepository;
    private final S3Uploader s3Uploader;

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
        return new UserResponseDto(entity);
    }

    @Transactional
    public String deleteUser(Long num) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }else{
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
    }

    @Transactional
    public String updateNickname(Long num, String nickname) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        } else {
            if(!nickname.equals("null") || !(nickname == null)){
                entity.updateNickname(nickname);
                return "modified";
            } else {
                return "nickname is null";
            }
        }
    }

    @Transactional
    public String updateProfileUrl(Long num, MultipartFile uploadFile) {
        String profileUrl;
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }
        else {
            try{
                profileUrl = s3Uploader.upload(uploadFile);

                if(profileUrl!=null) {
                    entity.updateProfileUrl(profileUrl);
                }
                else {
                    return "file type is not proper or is corrupted";
                }
            } catch (Exception e) {
                System.out.println("File exception");
                return "error occurred during upload" + e.getMessage();
            }
            return "modified";
        }
    }

    @Transactional
    public String deleteProfileUrl(Long num) {
        String absoluteUrl = new File("").getAbsolutePath()+"/src/main/resources/static";
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        } else {
            String currUrl = entity.getProfileUrl();
            if(!currUrl.equals("/uploads/defaultProfile.png")) {
                try {
                    String profileUrl = absoluteUrl + entity.getProfileUrl();
                    Path file = Paths.get(profileUrl);
                    Files.delete(file);
                } catch(Exception e) {
                    return "delete file error" + e.getMessage();
                }
            }
            entity.updateProfileUrl("/uploads/defaultProfile.png");
            return "deleted";
        }
    }

    @Transactional
    public String findUnivByNum(Long num) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }else {
            String authenticate = entity.getUniversity();
            if(authenticate==null) {
                return "not authenciated";
            } else {
                return authenticate;
            }
        }
    }

    @Transactional
    public String setUnivByNum(Long num, String univ) {
        LocalDateTime now = LocalDateTime.now();
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }else {
            if(!(univ==null) || !univ.equals("null")){
                entity.updateUniv(univ);
                entity.updateAuthenticatedDate(now);
                return "success";
            }else {
                return "univ is null";
            }
        }
    }
}
