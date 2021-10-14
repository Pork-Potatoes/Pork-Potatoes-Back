package com.matzipuniv.sinchon.config;

import com.matzipuniv.sinchon.domain.Folder;
import com.matzipuniv.sinchon.domain.ReviewRepository;
import com.matzipuniv.sinchon.service.AlarmService;
import com.matzipuniv.sinchon.service.FolderService;
import com.matzipuniv.sinchon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionAttributeListener;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@Controller
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final FolderService folderService;
    private final AlarmService alarmService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myyHandler(), "/myHandler").setAllowedOriginPatterns("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .withSockJS();
    }

    @Bean
    public WebSocketHandler myyHandler() {
        return new MyHandler(reviewRepository, userService, folderService, alarmService);
    }
}
