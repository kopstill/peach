package com.kopever.peach.service.eleme.web;

import com.kopever.peach.common.domain.HttpMessage;
import com.kopever.peach.common.domain.HttpResponse;
import com.kopever.peach.common.util.Jackson;
import com.kopever.peach.service.eleme.domain.HttpElemeMessage;
import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponRequestVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponResponseVO;
import com.kopever.peach.service.eleme.service.CouponService;
import com.kopever.peach.service.eleme.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class CouponController {

    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/coupon/cookie")
    public HttpResponse devoteCookie(@RequestParam("cookie_header") String cookieHeader) {
        logger.info("CouponController.devoteCookie.cookieHeader -> {}", cookieHeader);

        try {
            ElemeCookieVO cookieVO = CookieUtil.extractCookieModel(cookieHeader);
            logger.info("CouponController.devoteCookie.cookie -> {}", Jackson.toJson(cookieVO));

            if (CookieUtil.isCookieValid(cookieVO)) {
                couponService.saveElemeCouponCookie(cookieVO);
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

    @GetMapping("/coupon/lucky")
    public HttpResponse getLuckyCoupon(@Valid ElemeCouponRequestVO requestVO) {
        System.out.println(requestVO.getCouponUrl());
        System.out.println(requestVO.getPhoneNumber());

        HttpResponse response = new HttpResponse();
        ElemeCouponResponseVO responseVO = new ElemeCouponResponseVO();
        responseVO.setNickname("kopever");
        responseVO.setPhoneNumber("18888888888");
        responseVO.setAmount(new BigDecimal(5.6));
        response.setResult(responseVO);

        return response;
    }

}
