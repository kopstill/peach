package com.kopever.peach.service.eleme.service;

import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponResponseVO;

import java.io.IOException;
import java.math.BigInteger;

public interface ElemeCouponService {

    int saveElemeCouponCookie(String origin, ElemeCookieVO elemeCookieVO, BigInteger userId);

    ElemeCouponResponseVO getElemeLuckyCoupon(String mobileNumber, String couponUrl) throws IOException;

}
