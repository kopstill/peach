package com.kopever.peach.service.eleme.util;

import com.kopever.peach.common.util.Jackson;
import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CookieUtil {

    public static String extractCookie(String cookieHeader) throws UnsupportedEncodingException {
        String decodedCookieHeader = URLDecoder.decode(cookieHeader, "UTF-8");

        if (decodedCookieHeader.startsWith("Cookie:") || decodedCookieHeader.startsWith("cookie:")) {
            decodedCookieHeader.indexOf(":");
            String cookieValue = decodedCookieHeader.substring(
                    decodedCookieHeader.indexOf(":") + 1, decodedCookieHeader.length()
            ).trim();

            return doExtractCookie(cookieValue);
        } else if (decodedCookieHeader.startsWith("snsInfo")) {
            return doExtractCookie(decodedCookieHeader);
        }

        return null;
    }

    private static String doExtractCookie(String cookieValue) {
        String[] cookies = cookieValue.split("[;]");

        for (String cookie : cookies) {
            if (cookie.startsWith("snsInfo")) {
                return splitCookie(cookie);
            }
        }

        return null;
    }

    private static String splitCookie(String cookie) {
        String[] split = cookie.split("[=]");
        return split[1];
    }

    public static ElemeCookieVO extractCookieModel(String cookieHeader) throws UnsupportedEncodingException {
        return Jackson.fromJson(extractCookie(cookieHeader), ElemeCookieVO.class);
    }

    public static boolean isCookieValid(ElemeCookieVO cookieVO) {
        return !StringUtils.isAllBlank(cookieVO.getElemeKey(), cookieVO.getOpenid());
    }

}
