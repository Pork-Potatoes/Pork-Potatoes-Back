package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.config.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class SocketController {
    private final HttpServletRequest httpServletRequest;

    @GetMapping("/likeTest")
    public String likeTest() {
        return "test";
    }
}
