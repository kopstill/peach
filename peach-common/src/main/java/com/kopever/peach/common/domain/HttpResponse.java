package com.kopever.peach.common.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Lullaby on 2018/5/31
 */
@Getter
@Setter
public class HttpResponse<T> {

    private int code;

    private String message;

    private T result;

    public HttpResponse() {
        code = HttpMessage.SUCCESS.getCode();
        message = HttpMessage.SUCCESS.getMessage();
    }

    public HttpResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpResponse(HttpMessage httpMessage) {
        code = httpMessage.getCode();
        message = httpMessage.getMessage();
    }

}
