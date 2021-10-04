package com.matzipuniv.sinchon.config;

import com.matzipuniv.sinchon.domain.User;
import com.matzipuniv.sinchon.domain.UserRepository;
import com.matzipuniv.sinchon.service.UserService;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MyHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    //로그인 한 인원 전체
    private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
    // 1:1로 할 경우
    private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {//클라이언트와 서버가 연결
        // TODO Auto-generated method stub
        logger.info("Socket 연결");
        sessions.add(session);
        logger.info(currentUserID(session));//현재 접속한 사람
        userSessionsMap.put(currentUserID(session),session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();//자바스크립트에서 넘어온 Msg

        if(msg != null) {
            String[] msgs = msg.split(",");
            if (msgs != null && msgs.length == 4) {
                String bid = msgs[0];//게시물 번호
                String receiver = msgs[1];//글 작성자
                String count = msgs[2];//0이면 좋아요 취소 1이면 좋아요
                String btitle = msgs[3];//게시물 제목
                String comment = "";
                if (count.equals("1")) {
                    comment = "을/를 좋아합니다.";
                } else if (count.equals("2")) {
                    comment = "을/를 스크랩했습니다";
                }

                String sendUser = currentUserNick(session);

                WebSocketSession receiversession = userSessionsMap.get(receiver);//글 작성자가 현재 접속중인가 체크

                if (receiversession != null) {
                    String receiveUser = currentUserNick(receiversession);
                    TextMessage txtmsg = new TextMessage(sendUser + "님이 " + receiveUser + "님의 " + btitle + comment);
                    receiversession.sendMessage(txtmsg);//작성자에게 알려줍니다
                } else {
                    TextMessage txtmsg = new TextMessage(sendUser + "님이 " + receiver + "님의 " + btitle + comment);
                    session.sendMessage(txtmsg);//보내지는지 체크하기
                }

            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {//연결 해제
        // TODO Auto-generated method stub
        logger.info("Socket 끊음");
        sessions.remove(session);
        userSessionsMap.remove(currentUserID(session),session);
    }

    private String currentUserID(WebSocketSession session) {
        String mid = session.getId();
        Map<String, Object> map = session.getAttributes();
        SessionUser user = (SessionUser) map.get("user");
        String identifyCode = user.getUserNum().toString();
        return identifyCode;
    }

    private String currentUserNick(WebSocketSession session) {
        String mid = session.getId();
        Map<String, Object> map = session.getAttributes();
        SessionUser user = (SessionUser) map.get("user");
        String nickname = user.getName();
        return nickname;
    }
}
