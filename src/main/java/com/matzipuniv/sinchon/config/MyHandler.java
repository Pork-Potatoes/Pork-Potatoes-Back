package com.matzipuniv.sinchon.config;

import com.matzipuniv.sinchon.domain.Review;
import com.matzipuniv.sinchon.domain.ReviewRepository;
import com.matzipuniv.sinchon.domain.User;
import com.matzipuniv.sinchon.domain.UserRepository;
import com.matzipuniv.sinchon.service.FolderService;
import com.matzipuniv.sinchon.service.ReviewService;
import com.matzipuniv.sinchon.service.UserService;
import com.matzipuniv.sinchon.web.dto.FolderResponseDto;
import com.matzipuniv.sinchon.web.dto.ReviewResponseDto;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
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

@RequiredArgsConstructor
@RestController
public class MyHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final FolderService folderService;

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
        Long userNum = Long.parseLong("1");
        String content = "";

        if(msg != null) {
            String[] msgs = msg.split(",");
            if (msgs != null && msgs.length == 2) {
                String bid = msgs[0];//게시물 번호
                String count = msgs[1];//1이면 좋아요 2이면 스크랩
                String comment = "";
                if (count.equals("1")) {
                    comment = "을/를 좋아합니다.";
                    Review review = reviewRepository.findReviewByReviewNum(Long.parseLong(bid));
                    userNum = review.getUser().getUserNum();
                    content = review.getContent();
                    if(content.length() > 10) {
                        content = content.substring(0,10);
                    }

                } else if (count.equals("2")) {
                    comment = "을/를 스크랩했습니다";
                    FolderResponseDto folder = folderService.findById(Long.parseLong(bid));
                    userNum = folder.getUser().getUserNum();
                    content = folder.getTitle();
                    if(content.length() > 10) {
                        content = content.substring(0,10);
                    }
                }

                String sendUser = currentUserNick(session);
                String userName = userService.findByNum(userNum).getNickname();

                WebSocketSession receiversession = userSessionsMap.get(userNum.toString());//글 작성자가 현재 접속중인가 체크


                if (receiversession != null) {
                    String receiveUser = currentUserNick(receiversession);
                    TextMessage txtmsg = new TextMessage(sendUser + "님이 " + userName + "님의 " + content + comment);
                    receiversession.sendMessage(txtmsg);//작성자에게 알려줍니다
                } else {
                    TextMessage txtmsg = new TextMessage(sendUser + "님이 " + userName + "님의 " + content + comment);
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
