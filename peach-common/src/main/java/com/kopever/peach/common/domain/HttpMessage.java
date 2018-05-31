package com.kopever.peach.common.domain;

import lombok.Getter;

/**
 * Created by Lullaby on 2018/5/31
 */
public enum HttpMessage {

    SUCCESS(0, "Success");

    HttpMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Getter
    private int code;

    @Getter
    private String message;

}
