package com.kopever.peach.service.eleme;

import com.kopever.peach.common.util.OkHttpMediaType;
import com.kopever.peach.common.util.OkHttpUtil;
import org.junit.Test;

import java.io.IOException;

public class OkhttpTest {

    @Test
    public void testPut() throws IOException {
        String url = "https://h5.ele.me/restapi//v1/weixin/%s/phone";
        String body = "{\"sign\":\"7ed6e21ba4224a87f84ffd7dfa3f24d3\",\"phone\":\"12345678901\"}";
        String openid = "oEGLvjmQFldzXZZu2sw-oF7NuBFY";
        String result = OkHttpUtil.put(String.format(url, openid), body, OkHttpMediaType.TEXT);
        System.out.println(result);
    }

}
