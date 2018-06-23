package com.kopever.peach.service.eleme.web;

import com.kopever.peach.common.Jackson;
import com.kopever.peach.service.eleme.client.UserClient;
import com.kopever.peach.service.eleme.domain.HttpElemeMessage;
import com.kopever.peach.service.eleme.domain.data.ElemeCookieDO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponRequestVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponResponseVO;
import com.kopever.peach.service.eleme.service.ElemeCouponService;
import com.kopever.peach.service.eleme.util.ElemeCookieUtil;
import com.kopever.peach.service.framework.domain.HttpMessage;
import com.kopever.peach.service.framework.domain.HttpResponse;
import com.kopever.peach.service.framework.util.ResponseUtil;
import com.kopever.peach.domain.user.vo.PeachUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;

@RestController
public class ElemeCouponController {

    private static final Logger logger = LoggerFactory.getLogger(ElemeCouponController.class);

    private final ElemeCouponService couponService;

    private final UserClient userClient;

    public ElemeCouponController(ElemeCouponService couponService, UserClient userClient) {
        this.couponService = couponService;
        this.userClient = userClient;
    }

    @PostMapping("/coupon/cookie")
    public HttpResponse devoteCookie(
            @RequestParam("cookie") String cookie,
            @RequestParam(value = "user_id", required = false, defaultValue = "0") BigInteger userId) {
        logger.info("CouponController.devoteCookie.cookie -> {}", cookie);
        try {
            ElemeCookieVO cookieVO = ElemeCookieUtil.extractCookieModel(cookie);

            if (ElemeCookieUtil.isCookieValid(cookieVO)) {
                ElemeCookieDO cookieDO = couponService.getElemeCouponCookieByOpenid(cookieVO.getOpenid());
                if (cookieDO != null) {
                    return new HttpResponse(
                            HttpElemeMessage.COOKIE_EXISTED.getCode(), HttpElemeMessage.COOKIE_EXISTED.getMessage());
                }

                int result = couponService.saveElemeCouponCookie(cookie, cookieVO, userId);
                if (result != 1) {
                    return new HttpResponse(HttpMessage.FAILURE);
                }

                return new HttpResponse();
            }

            return new HttpResponse(
                    HttpElemeMessage.DEVOTE_COOKIE_FAILED.getCode(), HttpElemeMessage.DEVOTE_COOKIE_FAILED.getMessage());
        } catch (Exception e) {
            logger.error("CouponController.devoteCookie.Exception", e);
            return new HttpResponse(HttpMessage.EXCEPTION);
        }
    }

    @PostMapping("/coupon/lucky")
    public HttpResponse getLuckyCoupon(@Valid ElemeCouponRequestVO requestVO) {
        HttpResponse<PeachUserVO> userResponse = userClient.getUserByUsername(requestVO.getUsername());
        logger.info("CouponController.getLuckyCoupon.userResponse -> {}", Jackson.toJson(userResponse));

        try {
            if (ResponseUtil.isSuccess(userResponse)) {
                PeachUserVO userVO = ResponseUtil.getResult(userResponse);

                if (userVO.getSecretKey().equals(requestVO.getSecretKey())) {
                    ElemeCouponResponseVO responseVO =
                            couponService.getElemeLuckyCoupon(requestVO.getMobileNumber(), requestVO.getCouponUrl());

                    if (responseVO == null || !responseVO.getIsSuccess()) {
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
        } catch (Exception e) {
            logger.error("CouponController.getLuckyCoupon.Exception", e);
            return new HttpResponse(HttpMessage.EXCEPTION);
        }
    }

}
