package com.kopever.peach.common.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpUtil {

    private static OkHttpClient client = new OkHttpClient();

    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String post(String url, String body, OkHttpMediaType mediaType) throws IOException {
        RequestBody requestBody = RequestBody.create(mediaType.getMediaType(), body);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


}
