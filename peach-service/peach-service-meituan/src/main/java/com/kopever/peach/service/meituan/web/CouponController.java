package com.kopever.peach.service.meituan.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponController {

    @GetMapping("/coupon")
    public String coupon() {
        return "Wow, what a big coupon!";
    }

}
