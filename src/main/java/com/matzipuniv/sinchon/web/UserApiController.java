package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.UserService;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @GetMapping("/api/users/{userNum}")
    public UserResponseDto findByNum(@PathVariable Long userNum){
        return userService.findByNum(userNum);
    }

}
