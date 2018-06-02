package com.kopever.peach.service.eleme;

import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.util.CookieUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class CookieTest {

    @Test
    public void testGetCookie() throws UnsupportedEncodingException {
        String cookieHeader = "Cookie: snsInfo[wx2a416286e96100ed]=%7B%22city%22%3A%22%22%2C%22country%22%3A%22%E6%B3%BD%E8%A5%BF%E5%B2%9B%22%2C%22eleme_key%22%3A%222d5ced3d20546b44fda6fa64f5b1044e%22%2C%22headimgurl%22%3A%22http%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FB2lbKwd3ufL4HWibmqIvsh5hHhETTUm1v1GKJWQa4l7iaP1vvJ10eEtGIDiaA3IlZmD3AyHT5qHm4icgYGPIyH8k5Q%2F132%22%2C%22language%22%3A%22zh_CN%22%2C%22nickname%22%3A%22kopever%22%2C%22openid%22%3A%22oEGLvjh2WrbnkReLy7zex5KgTJ9A%22%2C%22privilege%22%3A%5B%5D%2C%22province%22%3A%22%22%2C%22sex%22%3A1%2C%22unionid%22%3A%22o_PVDuH3eaTT4SbQ1JVZsvruNbJQ%22%2C%22name%22%3A%22kopever%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FB2lbKwd3ufL4HWibmqIvsh5hHhETTUm1v1GKJWQa4l7iaP1vvJ10eEtGIDiaA3IlZmD3AyHT5qHm4icgYGPIyH8k5Q%2F132%22%7D; _utrace=a8f0c1313ec180330d5429540432e704_2018-05-16; ubt_ssid=o5skz751cmu8pudio6i6dslkhj2tv46e_2018-05-16; perf_ssid=wniu00hogwf9zwgu0qrrnuhfv1t1zq3r_2018-05-16";
        String result = CookieUtil.extractCookie(cookieHeader);
        System.out.println(result);

        ElemeCookieVO elemeCookieVO = CookieUtil.extractCookieModel(cookieHeader);
        System.out.println(CookieUtil.isCookieValid(elemeCookieVO));
    }

}
