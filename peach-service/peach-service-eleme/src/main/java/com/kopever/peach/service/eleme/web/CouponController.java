package com.kopever.peach.service.eleme.web;

import com.kopever.peach.common.domain.HttpResponse;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponRequestVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CouponController {

    @GetMapping("/coupon")
    public HttpResponse coupon(ElemeCouponRequestVO requestVO) {
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
