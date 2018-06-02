package com.kopever.peach.service.eleme.domain;

import lombok.Getter;

public enum HttpElemeMessage {

    DEVOTE_COOKIE_FAILED(10600, "Cookie贡献失败，请检查Cookie格式"),
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
