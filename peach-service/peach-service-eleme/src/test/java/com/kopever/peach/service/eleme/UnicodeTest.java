package com.kopever.peach.service.eleme;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeTest {

    @Test
    public void testUnicodeToString() {
        String str = "\\u8bf7\\u68c0\\u67e5\\u624b\\u673a\\u53f7\\u7801\\u683c\\u5f0f\\u662f\\u5426\\u6709\\u8bef";

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }

        System.out.println(str);
    }

}
