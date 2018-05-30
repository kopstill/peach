package com.kopever.peach.test;

import org.junit.Test;
import org.springframework.web.util.HtmlUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Lullaby on 2018/5/30
 */
public class TempTest {

    @Test
    public void testDecodeUrl() throws UnsupportedEncodingException {
        String encodedUrl = "https://h5.ele.me/hongbao/#hardware_id=&amp;is_lucky_group=True&amp;lucky_number=10&amp;track_id=&amp;platform=0&amp;sn=10f02f832aabf4fb&amp;theme_id=2585&amp;device_id=&amp;refer_user_id=19539192";
        String decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8");
        System.out.println(decodedUrl);
    }

    @Test
    public void testEscapeHtml() {
        String escapedHtml = HtmlUtils.htmlUnescape("https://h5.ele.me/hongbao/#hardware_id=&amp;is_lucky_group=True&amp;lucky_number=10&amp;track_id=&amp;platform=0&amp;sn=10f02f832aabf4fb&amp;theme_id=2585&amp;device_id=&amp;refer_user_id=19539192");
        System.out.println(escapedHtml);
    }

}
