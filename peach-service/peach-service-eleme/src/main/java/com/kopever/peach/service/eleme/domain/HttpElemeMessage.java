package com.kopever.peach.service.eleme.domain;

import lombok.Getter;

public enum HttpElemeMessage {

    DEVOTE_COOKIE_FAILED(10601, "Cookie贡献失败，请检查Cookie格式"),
    INVALID_SECRET_KEY(10602, "用户密钥错误"),
    ;

    HttpElemeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Getter
    private int code;

    @Getter
    private String message;

}
