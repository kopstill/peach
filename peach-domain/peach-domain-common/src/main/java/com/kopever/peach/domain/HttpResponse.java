package com.kopever.peach.domain;

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
        this.code = HttpMessage.SUCCESS.getCode();
        this.message = HttpMessage.SUCCESS.getMessage();
    }

    public HttpResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpResponse(HttpMessage httpMessage) {
        this.code = httpMessage.getCode();
        this.message = httpMessage.getMessage();
    }

    public HttpResponse<T> setCodeMessage(int code, String message) {
        this.code = code;
        this.message = message;

        return this;
    }

}
