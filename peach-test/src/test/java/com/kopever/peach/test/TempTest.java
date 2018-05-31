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
        String encodedUrl = "https%3a%2f%2fh5.ele.me%2fhongbao%2f%23hardware_id%3d%26is_lucky_group%3dTrue%26lucky_number%3d10%26track_id%3d%26platform%3d0%26sn%3d10f02f832aabf4fb%26theme_id%3d2585%26device_id%3d%26refer_user_id%3d19539192";
        String decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8");
        System.out.println(decodedUrl);
    }

    @Test
    public void testEscapeHtml() {
        System.out.println(HtmlUtils.htmlUnescape("https://h5.ele.me/hongbao/#hardware_id=&amp;is_lucky_group=True&amp;lucky_number=10&amp;track_id=&amp;platform=0&amp;sn=10f02f832aabf4fb&amp;theme_id=2585&amp;device_id=&amp;refer_user_id=19539192"));
        System.out.println(HtmlUtils.htmlUnescape("https://h5.ele.me/hongbao/#hardware_id=&is_lucky_group=True&lucky_number=10&track_id=&platform=0&sn=10f02f832aabf4fb&theme_id=2585&device_id=&refer_user_id=19539192"));
    }

}
