package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.*;
import com.matzipuniv.sinchon.web.dto.OAuthAttributes;
import com.matzipuniv.sinchon.web.dto.SessionUser;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService  implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final PinRepository pinRepository;
    private final FileHandler fileHandler;
    private final HttpSession httpSession;

    @Transactional
    public List<UserResponseDto> findAll() {
        List<User> entities = userRepository.findAllByDeleteFlagFalse();
        if(entities==null) {
            System.out.println("유저가 없습니다");
        }
        List<UserResponseDto> responseDtoList = new ArrayList<>();
        for(User entity : entities){
            responseDtoList.add(new UserResponseDto(entity));
        }
        return responseDtoList;
    }

    @Transactional
    public UserResponseDto findByNum(Long num){
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            System.out.println("없는 유저입니다. user_num = "+num);
        }
        return new UserResponseDto(entity);
    }

    @Transactional
    public String deleteUser(Long num) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }else{
            List<Pin> pins = pinRepository.findByUser(entity);
            try {
                entity.updateDeleteFlag();
                for(Pin pin : pins) {
                    pinRepository.delete(pin);
                }
                return "user"+num+" deleted";
            }
            catch(Exception e) {
                return "error occured";
            }
        }
    }

    @Transactional
    public String updateNickname(Long num, String nickname) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        } else {
            if(!nickname.equals("null") || !(nickname == null)){
                entity.updateNickname(nickname);
                return "modified";
            } else {
                return "nickname is null";
            }
        }
    }

    @Transactional
    public String updateProfileUrl(Long num, MultipartFile uploadFile) {
        String profileUrl;
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }
        else {
            try{
                profileUrl = fileHandler.parseFileInfo(uploadFile);
                if(profileUrl!=null) {
                    entity.updateProfileUrl(profileUrl);
                }
                else {
                    return "file type is not proper or is corrupted";
                }
            } catch (Exception e) {
                System.out.println("File exception");
                return "error occurred during upload";
            }
            return "modified";
        }
    }

    @Transactional
    public String deleteProfileUrl(Long num) {
        String absoluteUrl = new File("").getAbsolutePath()+"/src/main/resources/static";
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        } else {
            String currUrl = entity.getProfileUrl();
            if(!currUrl.equals("/uploads/defaultProfile.png")) {
                try {
                    String profileUrl = absoluteUrl + entity.getProfileUrl();
                    Path file = Paths.get(profileUrl);
                    Files.delete(file);
                } catch(Exception e) {
                    return "delete file error" + e.getMessage();
                }
            }
            entity.updateProfileUrl("/uploads/defaultProfile.png");
            return "deleted";
        }
    }

    @Transactional
    public String findUnivByNum(Long num) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }else {
            String authenticate = entity.getUniversity();
            if(authenticate==null) {
                return "not authenciated";
            } else {
                return authenticate;
            }
        }
    }

    @Transactional
    public String setUnivByNum(Long num, String univ) {
        User entity = userRepository.findByUserNumAndDeleteFlagFalse(num);
        if(entity==null) {
            return "없는 유저입니다. user_num = "+num;
        }else {
            if(!(univ==null) || !univ.equals("null")){
                entity.updateUniv(univ);
                return "success";
            }else {
                return "univ is null";
            }
        }
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스 구분
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // 로그인 진행 시의 pk

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes()); //OAuth2User의 attribute 담을 클래스

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user",new SessionUser(user));
        String userRole = Role.MEMBER.getValue();
        if(user.getUniversity()!=null) {
            userRole = Role.STUDENT.getValue();
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(userRole)),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail());
        if(user!=null){
            user.updateNickname(attributes.getName());
            user.updateProfileUrl(attributes.getPicture());
        } else{
            attributes.toEntity();
        }
        return userRepository.save(user);
    }
}
