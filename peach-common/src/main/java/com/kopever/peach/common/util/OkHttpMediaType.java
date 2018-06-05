package com.kopever.peach.common.util;

import lombok.Getter;
import okhttp3.MediaType;

public enum OkHttpMediaType {

    JSON(MediaType.parse("application/json; charset=utf-8"));

    @Getter
    private MediaType mediaType;

    OkHttpMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

}
