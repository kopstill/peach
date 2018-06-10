package com.kopever.peach.common.util;

import okhttp3.*;

import java.io.IOException;

public class OkHttpUtil {

    private static OkHttpClient client = new OkHttpClient();

//    public static String get(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        return responseBodyString(request);
//    }

    public static String post(String url, String body, OkHttpMediaType mediaType) throws IOException {
        RequestBody requestBody = RequestBody.create(mediaType.getMediaType(), body);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return responseBodyString(request);
    }

    public static String put(String url, String body, OkHttpMediaType mediaType) throws IOException {
        RequestBody requestBody = RequestBody.create(mediaType.getMediaType(), body);
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();
        return responseBodyString(request);
    }

    private static String responseBodyString(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                return null;
            }
            return responseBody.string();
        }
    }

}
