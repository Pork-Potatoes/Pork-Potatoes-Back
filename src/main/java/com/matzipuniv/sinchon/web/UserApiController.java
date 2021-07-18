package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.UserService;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
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

    @PatchMapping("/{userNum}/nickname")
    public String updateNickname(@PathVariable Long userNum, @RequestBody String nickname) {
        return userService.updateNickname(userNum, nickname);
    }

    @PatchMapping("/{userNum}/image")
    public String updateProfile(@PathVariable Long userNum, @RequestParam("uploadFile")MultipartFile uploadFile) {
        return userService.updateProfileUrl(userNum, uploadFile);
    }

    @DeleteMapping("/{userNum}/image")
    public String deleteProfile(@PathVariable Long userNum) {
        return userService.deleteProfileUrl(userNum);
    }

}
