package com.matzipuniv.sinchon.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    STUDENT("ROLE_STUDENT"),
    MEMBER("ROLE_MEMBER");

    private String value;
}
