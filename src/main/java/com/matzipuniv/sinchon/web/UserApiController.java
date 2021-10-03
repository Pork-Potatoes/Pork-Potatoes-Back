package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.config.SessionUser;
import com.matzipuniv.sinchon.service.UserService;
import com.matzipuniv.sinchon.web.dto.MailDto;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import com.matzipuniv.sinchon.web.dto.UserUnivRequestDto;
import com.matzipuniv.sinchon.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    @GetMapping("")
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userNum}")
    public UserResponseDto findByNum(@PathVariable Long userNum){
        return userService.findByNum(userNum);
    }

    @DeleteMapping("/{userNum}")
    public String deleteByNum(@PathVariable Long userNum) {
        return userService.deleteUser(userNum);
    }

    @PatchMapping("/{userNum}/nickname")
    public String updateNicknameByNum(@PathVariable Long userNum, @RequestBody UserUpdateRequestDto nickname) {
        return userService.updateNickname(userNum, nickname);
    }

    @PatchMapping("/{userNum}/image")
    public String updateProfileByNum(@PathVariable Long userNum, @RequestParam("uploadFile")MultipartFile uploadFile) {
        return userService.updateProfile(userNum, uploadFile);
    }

    @DeleteMapping("/{userNum}/image")
    public String deleteProfileByNum(@PathVariable Long userNum) {
        return userService.deleteProfile(userNum);
    }

    @GetMapping("/{userNum}/university")
    public String findUnivByNum(@PathVariable Long userNum) {
        return userService.findUniv(userNum);
    }

    @PostMapping("/{userNum}/authenticate")
    public String sendMail(@PathVariable Long userNum, @RequestBody UserUnivRequestDto univDto) {
        UserResponseDto userDto = userService.findByNum(userNum);
        String nickname = userDto.getNickname();
        String university = univDto.getUniversity();
        String email = univDto.getEmail();

        String title = "[맛집전공 대학인증] " + nickname +"님에게 보내는 인증메일입니다";
        String msg = "안녕하세요 " + nickname + "님! 해당 링크로 접속하여 인증절차를 마무리해주세요!";
        MailDto mailDto = new MailDto(email, title, msg);
        return userService.mailSend(userNum, university, mailDto);
    }

    @GetMapping("/{userNum}/authenticate")
    public String confirmMail(@PathVariable Long userNum, @RequestParam String mailKey) {
        return userService.mailConfirm(userNum, mailKey);
    }

    @GetMapping("/islogged")
    public String logged() {
        return userService.isLogged();
    }


}
