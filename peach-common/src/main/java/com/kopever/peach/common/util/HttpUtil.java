package com.kopever.peach.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lullaby on 2018/5/31
 */
public class HttpUtil {

    public static Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();

        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = enums.nextElement();
            parameters.put(paramName, request.getParameter(paramName));
        }

        return parameters;
    }

}
