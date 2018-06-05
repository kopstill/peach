package com.kopever.peach.service.eleme.web;

import com.kopever.peach.common.Jackson;
import com.kopever.peach.service.eleme.client.UserClient;
import com.kopever.peach.service.eleme.domain.HttpElemeMessage;
import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponRequestVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponResponseVO;
import com.kopever.peach.service.eleme.service.CouponService;
import com.kopever.peach.service.eleme.util.CookieUtil;
import com.kopever.peach.service.framework.domain.HttpMessage;
import com.kopever.peach.service.framework.domain.HttpResponse;
import com.kopever.peach.service.framework.exception.HttpResponseFailedException;
import com.kopever.peach.service.framework.exception.HttpResponseNullException;
import com.kopever.peach.service.framework.util.ResponseUtil;
import com.kopever.peach.service.user.domain.vo.PeachUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CouponController {

    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);

    private final CouponService couponService;

    private final UserClient userClient;

    public CouponController(CouponService couponService, UserClient userClient) {
        this.couponService = couponService;
        this.userClient = userClient;
    }

    @PostMapping("/coupon/cookie")
    public HttpResponse devoteCookie(@RequestParam("cookie") String cookie) {
        logger.info("CouponController.devoteCookie.cookie -> {}", cookie);

        try {
            ElemeCookieVO cookieVO = CookieUtil.extractCookieModel(cookie);

            if (CookieUtil.isCookieValid(cookieVO)) {
                int result = couponService.saveElemeCouponCookie(cookieVO);

                if (result != 1) {
                    return new HttpResponse(HttpMessage.FAILURE);
                }

                return new HttpResponse();
            }

            return new HttpResponse(
                    HttpElemeMessage.DEVOTE_COOKIE_FAILED.getCode(),
                    HttpElemeMessage.DEVOTE_COOKIE_FAILED.getMessage());
        } catch (Exception e) {
            logger.error("CouponController.devoteCookie.Exception", e);

            return new HttpResponse(HttpMessage.EXCEPTION);
        }
    }

    @PostMapping("/coupon/lucky")
    public HttpResponse getLuckyCoupon(@Valid ElemeCouponRequestVO requestVO) {
        HttpResponse<PeachUserVO> userResponse = userClient.getUserByMobileNumber(requestVO.getMobileNumber());
        logger.info("CouponController.getLuckyCoupon.userResponse -> {}", Jackson.toJson(userResponse));

        try {
            if (ResponseUtil.isSuccess(userResponse)) {
                PeachUserVO userVO = ResponseUtil.getResult(userResponse);

                if (userVO.getSecretKey().equals(requestVO.getSecretKey())) {
                    ElemeCouponResponseVO responseVO = couponService.getElemeLuckyCoupon(requestVO, userVO);

                    if (responseVO == null || !responseVO.getIsLucky()) {
                        return new HttpResponse<>(HttpMessage.FAILURE).setResult(responseVO);
                    }

                    return new HttpResponse<>().setResult(responseVO);
                } else {
                    return new HttpResponse().setCodeMessage(
                            HttpElemeMessage.INVALID_SECRET_KEY.getCode(),
                            HttpElemeMessage.INVALID_SECRET_KEY.getMessage());
                }
            }

            return userResponse;
        } catch (HttpResponseNullException | HttpResponseFailedException e) {
            logger.error(e.getMessage());
            return new HttpResponse(HttpMessage.EXCEPTION);
        }
    }

}
