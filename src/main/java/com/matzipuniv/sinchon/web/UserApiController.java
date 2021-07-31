package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.UserService;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import com.matzipuniv.sinchon.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{userNum}/authenticate")
    public String findUnivByNum(@PathVariable Long userNum) {
        return userService.findUniv(userNum);
    }

    @PostMapping("/{userNum}/authenticate")
    public String setUnivByNum(@PathVariable Long userNum, @RequestBody String univ) {
        return userService.setUniv(userNum, univ);
    }

}
