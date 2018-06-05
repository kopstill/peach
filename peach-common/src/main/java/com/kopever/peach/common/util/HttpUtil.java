package com.kopever.peach.common.util;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
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

    public static Map<String, String> getRefParams(String url) throws MalformedURLException {
        URL urlobj = new URL(url);
        String ref = urlobj.getRef();
        String[] refParams = ref.split("[&]");

        Map<String, String> result = new HashMap<>();
        for (String param : refParams) {
            String[] paramKV = param.split("[=]");
            if (paramKV.length == 1) {
                result.put(paramKV[0], null);
            } else {
                result.put(paramKV[0], paramKV[1]);
            }
        }

        return result;
    }

}
