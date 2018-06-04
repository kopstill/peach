package com.kopever.peach.service.framework.spring.handler;

import com.kopever.peach.common.util.HttpUtil;
import com.kopever.peach.service.framework.domain.HttpMessage;
import com.kopever.peach.service.framework.domain.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public HttpResponse exceptionHandler(HttpServletRequest request, Exception exception) {
        logger.error("GlobalExceptionHandler.exceptionHandler", exception);
        logger.error("Exception url -> {}", request.getRequestURL().toString());

        Map<String, String> parameters = HttpUtil.getParameters(request);
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            logger.error(entry.getKey() + ": {}", entry.getValue());
        }

        return new HttpResponse(HttpMessage.EXCEPTION);
    }

    @ExceptionHandler(value = BindException.class)
    public HttpResponse<List> bindExceptionHandler(HttpServletRequest request, BindException bindException) {
        logger.error("GlobalExceptionHandler.bindExceptionHandler", bindException);
        logger.error("Exception url -> {}", request.getRequestURL().toString());

        BindingResult bindingResult = bindException.getBindingResult();
        List<String> messages = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindException.getAllErrors();
            for (ObjectError error : allErrors) {
                if (error instanceof FieldError) {
                    messages.add(((FieldError) error).getField() + error.getDefaultMessage());
                } else {
                    messages.add(error.getDefaultMessage());
                }
            }
        }

        return new HttpResponse<List>(HttpMessage.EXCEPTION).setResult(messages);
    }

}
