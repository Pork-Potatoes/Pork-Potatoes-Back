package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.config.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class LoginApiController {
    private final HttpServletRequest httpServletRequest;

    @GetMapping("/")
    public String getUser() {
        HttpSession httpsession = httpServletRequest.getSession();
        SessionUser user = (SessionUser) httpsession.getAttribute("user");
        if(user != null) {
            Long response = user.getUserNum();
            return response.toString();
        }
        else {
            String fail = "fail";
            return fail;
        }
    }

}
