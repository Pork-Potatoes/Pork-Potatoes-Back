package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.User;
import com.matzipuniv.sinchon.domain.UserRepository;
import com.nimbusds.oauth2.sdk.Role;
import com.nimbusds.oauth2.sdk.util.date.SimpleDate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Autowired
    private UserRepository userRepository;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name,
                           String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String,Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver("id",attributes);
        }
        if("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        }
        return ofGoogle(userNameAttributeName,attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String)response.get("name"))
                .email((String)response.get("email"))
                .picture((String)response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> response = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");
        return OAuthAttributes.builder()
                .name((String)profile.get("nickname"))
                .email((String)response.get("email"))
                .picture((String)profile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public User toEntity(){//처음 가입할 때 User entity 생성
        LocalDateTime now = LocalDateTime.now();
        User newUser = new User(name, picture, 0, email, now);
        return newUser;
    }
}