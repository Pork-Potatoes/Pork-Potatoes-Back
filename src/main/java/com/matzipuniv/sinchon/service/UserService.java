package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.*;
import com.matzipuniv.sinchon.domain.User;
import com.matzipuniv.sinchon.web.dto.MailDto;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import com.matzipuniv.sinchon.web.dto.UserUpdateRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@AllArgsConstructor
@Service
public class UserService {
    private JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final PinRepository pinRepository;
    private final S3UploaderProfile s3UploaderProfile;
    private static final String FROM_ADDRESS = "matzipuniv@gmail.com";

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
        String univ = entity.getUniversity();
        LocalDateTime auth = entity.getAuthenticatedDate();
        if(entity==null) {
            System.out.println("없는 유저입니다. user_num = "+num);
        }
        if(univ!=null && auth!=null) {
            long dayGone = compareDay(LocalDateTime.now(), auth);
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
        String univ = entity.getUniversity();
        LocalDateTime date = entity.getAuthenticatedDate();
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }
        if(univ!=null && date!=null) {
            return "success";
        } else if(univ==null && date!=null) {
            return "re-authenticate";
        } else if(univ!=null && date==null) {
            return "authenticating";
        } else {
            return "not-authenticated";
        }
    }

    @Transactional
    public String mailSend(Long userNum, String univ, MailDto mailDto) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(userNum);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+userNum;
        }
        try {
            String auth = getAuthCode(6);
            MailHandler mailHandler = new MailHandler(mailSender);

            // 받는 사람
            mailHandler.setTo(mailDto.getAddress());
            // 보내는 사람
            mailHandler.setFrom(FROM_ADDRESS);
            // 제목
            mailHandler.setSubject(mailDto.getTitle());
            // HTML Layout
            String htmlContent = "<p> <img src ='http://d18omhl2ssqffk.cloudfront.net/banner-20210802145400.png' height='180' width='1000'> </p>" +
                    "<p>" + mailDto.getMessage() +"</p>" +
                    "<p><a href='https://matzipmajor.com/api/users/" + userNum + "/authenticate?mailKey=" + auth + "'>여기를 클릭하세요</a></p>";
            mailHandler.setText(htmlContent, true);

            mailHandler.send();
            entity.updateAuthKey(auth);
            entity.updateUniv(univ);
            return "success";
        }
        catch(Exception e){
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }

    @Transactional
    public String mailConfirm(Long userNum, String auth) {
        LocalDateTime now = LocalDateTime.now();
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(userNum);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+userNum;
        }
        String realAuth = entity.getAuthKey();
        if(realAuth.equals(auth)) {
            entity.updateAuthKey(null);
            entity.updateAuthenticatedDate(now);
            return "univ: " + entity.getUniversity() + "  date: "+entity.getAuthenticatedDate();
        } else {
            return "something went wrong!";
        }

    }

    public long compareDay(LocalDateTime now, LocalDateTime auth_date) {
        LocalDateTime now2 = now.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime auth_date2 = auth_date.truncatedTo(ChronoUnit.DAYS);
        return ChronoUnit.DAYS.between(auth_date2, now2);
    }

    private String getAuthCode(int size) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;
        while(buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }


}
