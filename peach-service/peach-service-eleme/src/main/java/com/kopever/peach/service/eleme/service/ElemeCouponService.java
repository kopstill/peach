package com.kopever.peach.service.eleme.service;

import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponResponseVO;

import java.io.IOException;
import java.math.BigInteger;

public interface ElemeCouponService {

    int saveElemeCouponCookie(ElemeCookieVO elemeCookieVO, Boolean isPrimary, BigInteger userId);

    ElemeCouponResponseVO getElemeLuckyCoupon(BigInteger userId, String mobileNumber, String couponUrl) throws IOException;

}
