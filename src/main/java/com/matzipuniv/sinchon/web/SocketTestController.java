package com.matzipuniv.sinchon.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SocketTestController {

    @GetMapping("/socketTest")
    public String wssTest() {
        return "test2";
    }

}
