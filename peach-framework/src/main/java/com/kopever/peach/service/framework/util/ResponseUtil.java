package com.kopever.peach.service.framework.util;

import com.kopever.peach.domain.HttpMessage;
import com.kopever.peach.domain.HttpResponse;
import com.kopever.peach.service.framework.exception.HttpResponseFailedException;
import com.kopever.peach.service.framework.exception.HttpResponseNullException;

public class ResponseUtil {

    public static boolean isSuccess(HttpResponse response) throws HttpResponseNullException {
        if (response == null) {
            throw new HttpResponseNullException("response is null");
        }

        return response.getCode() == HttpMessage.SUCCESS.getCode();
    }

    public static <T> T getResult(HttpResponse<T> response)
            throws HttpResponseNullException, HttpResponseFailedException {
        if (isSuccess(response)) {
            return response.getResult();
        }

        throw new HttpResponseFailedException("response failed");
    }

}
