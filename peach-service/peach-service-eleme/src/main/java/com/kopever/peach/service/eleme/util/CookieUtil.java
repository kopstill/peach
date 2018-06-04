package com.kopever.peach.service.eleme.util;

import com.kopever.peach.common.Jackson;
import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CookieUtil {

    public static String extractCookie(String cookie) throws UnsupportedEncodingException {
        String decodedCookie = URLDecoder.decode(cookie, "UTF-8");

        if (decodedCookie.startsWith("Cookie:") || decodedCookie.startsWith("cookie:")) {
            String cookieValue = decodedCookie.substring(
                    decodedCookie.indexOf(":") + 1, decodedCookie.length()).trim();

            return doExtractCookie(cookieValue);
        } else {
            return doExtractCookie(decodedCookie);
        }
    }

    private static String doExtractCookie(String cookieValue) {
        String[] cookies = cookieValue.split("[;]");

        for (String cookie : cookies) {
            if (cookie.trim().startsWith("snsInfo")) {
                return splitCookie(cookie);
            }
        }

        return null;
    }

    private static String splitCookie(String cookie) {
        String[] split = cookie.split("[=]");
        return split[1];
    }

    public static ElemeCookieVO extractCookieModel(String cookie) throws UnsupportedEncodingException {
        return Jackson.fromJson(extractCookie(cookie), ElemeCookieVO.class);
    }

    public static boolean isCookieValid(ElemeCookieVO cookieVO) {
        return !StringUtils.isAnyBlank(cookieVO.getElemeKey(), cookieVO.getOpenid());
    }

}
