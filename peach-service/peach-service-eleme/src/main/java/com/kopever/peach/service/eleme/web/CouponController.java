package com.kopever.peach.service.eleme.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CouponController {

    @GetMapping("/coupon")
    public String coupon(HttpServletRequest request) {
        String couponUrl = request.getParameter("coupon_url");
        System.out.println(couponUrl);

        return "Success";
    }

}
