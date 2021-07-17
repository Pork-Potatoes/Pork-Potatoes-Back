package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.UserService;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    @GetMapping("/{userNum}")
    public UserResponseDto findByNum(@PathVariable Long userNum){
        return userService.findByNum(userNum);
    }

    @PatchMapping("/{userNum}/nickname")
    public String updateNickname(@PathVariable Long userNum, @RequestBody String nickname) {
        return userService.updateNickname(userNum, nickname);
    }

    @PatchMapping("/{userNum}/image")
    public String updateProfile(@PathVariable Long userNum, @RequestParam("uploadFile")MultipartFile uploadFile) {
        return userService.updateProfileUrl(userNum, uploadFile);
    }

}
