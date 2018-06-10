package com.kopever.peach.service.eleme;

import com.kopever.peach.common.util.OkHttpMediaType;
import com.kopever.peach.common.util.OkHttpUtil;
import org.junit.Test;

import java.io.IOException;

public class OkhttpTest {

    @Test
    public void testPut() throws IOException {
        String url = "https://h5.ele.me/restapi//v1/weixin/%s/phone";
        String body = "{\"sign\":\"2d5ced3d20546b44fda6fa64f5b1044e\",\"phone\":\"136942674091\"}";
        String openid = "oEGLvjh2WrbnkReLy7zex5KgTJ9A";
        String result = OkHttpUtil.put(String.format(url, openid), body, OkHttpMediaType.TEXT);
        System.out.println(result);
    }

}
