package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.User;
import com.matzipuniv.sinchon.domain.UserRepository;
import com.matzipuniv.sinchon.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto findByNum(Long num){
        User entity = userRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. user_num = "+num));

        return new UserResponseDto(entity);
    }
}
