package com.matzipuniv.sinchon.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
public class SocketController {

    @GetMapping("/likeTest")
    public String likeTest() {
        return "test";
    }
}
