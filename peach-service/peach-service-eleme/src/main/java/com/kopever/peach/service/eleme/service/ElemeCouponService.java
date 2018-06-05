package com.kopever.peach.service.eleme.service;

import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponResponseVO;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;

public interface ElemeCouponService {

    int saveElemeCouponCookie(ElemeCookieVO elemeCookieVO, Boolean isPrimary, BigInteger userId);

    ElemeCouponResponseVO getElemeLuckyCoupon(BigInteger userId, String couponUrl)
            throws UnsupportedEncodingException, MalformedURLException;

}
