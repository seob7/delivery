package com.delivery.db.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {

    REGSITERED("등록"),
    UNREGISTERED("해지")
    ;

    private final String description;
}
