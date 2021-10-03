package com.matzipuniv.sinchon.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class CustomHandShake extends HttpSessionHandshakeInterceptor {

    @Autowired
    private HttpSession httpSession;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        System.out.println("Before handShake");

        ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
        System.out.println("URI: " + request.getURI());

        HttpServletRequest req = ssreq.getServletRequest();
        SessionUser user = (SessionUser) req.getSession().getAttribute("user");
        attributes.put("userEmail", user.getEmail());
        System.out.println("HttpSession 속 email: " + user.getEmail());

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        System.out.println("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
