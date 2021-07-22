package com.matzipuniv.sinchon.config;

import com.matzipuniv.sinchon.domain.User;
import com.matzipuniv.sinchon.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스 구분
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // 로그인 진행 시의 pk

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes()); //OAuth2User의 attribute 담을 클래스

        User user = saveOrUpdate(registrationId, attributes);

        httpSession.setAttribute("user",new SessionUser(user));
        String userRole = Role.MEMBER.getValue();
        if(user.getUniversity()!=null) {
            userRole = Role.STUDENT.getValue();
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(userRole)),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(String registrationId, OAuthAttributes attributes){
        User user = userRepository.findByEmailAndSocialLogin(attributes.getEmail(), registrationId);
        if(user!=null){
            return user;
        } else{
            return userRepository.save(attributes.toEntity(registrationId));
        }
    }
}
