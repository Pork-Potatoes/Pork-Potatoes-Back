package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.User;
import com.matzipuniv.sinchon.domain.UserRepository;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileHandler fileHandler;

    @Transactional
    public List<UserResponseDto> findAll() {
        List<User> entities = userRepository.findAll();
        List<UserResponseDto> responseDtoList = new ArrayList<>();
        for(User entity : entities){
            responseDtoList.add(new UserResponseDto(entity));
        }
        return responseDtoList;
    }

    @Transactional
    public UserResponseDto findByNum(Long num){
        User entity = userRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+num));

        return new UserResponseDto(entity);
    }

    @Transactional
    public String deleteUser(Long num) {
        User entity = userRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+num));
        try {
            entity.updateDeleteFlag();
            return "user"+num+" deleted";
        }
        catch(Exception e) {
            return "error occured";
        }
    }

    @Transactional
    public String updateNickname(Long userNum, String nickname) {
        User entity = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+userNum));

        if(!nickname.equals("null") || !(nickname == null)){
            entity.updateNickname(nickname);
        }
        return "modified";
    }

    @Transactional
    public String updateProfileUrl(Long userNum, MultipartFile uploadFile) {
        String profileUrl;
        User entity = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+userNum));
        try{
            profileUrl = fileHandler.parseFileInfo(uploadFile);
            entity.updateProfileUrl(profileUrl);

        } catch (Exception e) {
            System.out.println("File exception");
            return "error occurred during upload : either file type is not proper, or file is corrupted. Only upload png or jpeg.";
        }
        return "modified";
    }

    @Transactional
    public String deleteProfileUrl(Long userNum) {
        String absoluteUrl = new File("").getAbsolutePath()+"/src/main/resources/static";
        User entity = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+userNum));

        String currUrl = entity.getProfileUrl();
        if(!currUrl.equals("/uploads/defaultProfile.png")) {
            try {
                String profileUrl = absoluteUrl + entity.getProfileUrl();
                Path file = Paths.get(profileUrl);
                Files.delete(file);
            } catch(Exception e) {
                System.out.println("delete file error" + e.getMessage());
            }
        }
        entity.updateProfileUrl("/uploads/defaultProfile.png");
        return "deleted";
    }

    @Transactional
    public String findUnivByNum(Long num) {
        User entity = userRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+num));
        String authenticate = entity.getUniversity();
        if(authenticate==null) {
            return "not authenciated";
        } else {
            return authenticate;
        }
    }

    @Transactional
    public String setUnivByNum(Long num, String univ) {
        User entity = userRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+num));

        if(!(univ==null) || !univ.equals("null")){
            entity.updateUniv(univ);
        }
        return "success";
    }
}
