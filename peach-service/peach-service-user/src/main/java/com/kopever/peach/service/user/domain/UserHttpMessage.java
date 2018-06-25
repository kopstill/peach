package com.kopever.peach.service.user.domain;

import lombok.Getter;

public enum UserHttpMessage {

    USER_NOT_EXIST(10401, "用户不存在"),
    GET_USER_EXCEPTION(10402, "获取用户信息异常"),
    ;

    UserHttpMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Getter
    private int code;

    @Getter
    private String message;

}
