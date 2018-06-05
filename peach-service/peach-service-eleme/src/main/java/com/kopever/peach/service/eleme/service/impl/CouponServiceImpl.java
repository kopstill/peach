package com.kopever.peach.service.eleme.service.impl;

import com.kopever.peach.common.Dozer;
import com.kopever.peach.service.eleme.dao.ElemeCookieMapper;
import com.kopever.peach.service.eleme.domain.data.ElemeCookieDO;
import com.kopever.peach.service.eleme.domain.data.ElemeEnum;
import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponRequestVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponResponseVO;
import com.kopever.peach.service.eleme.service.CouponService;
import com.kopever.peach.service.user.domain.vo.PeachUserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CouponServiceImpl implements CouponService {

    private final ElemeCookieMapper elemeCookieMapper;

    public CouponServiceImpl(ElemeCookieMapper elemeCookieMapper) {
        this.elemeCookieMapper = elemeCookieMapper;
    }

    @Override
    public int saveElemeCouponCookie(ElemeCookieVO cookieVO) {
        String privilege = StringUtils.join(cookieVO.getPrivilege(), ',');
        cookieVO.setPrivilege(null);

        ElemeCookieDO cookieDO = Dozer.map(cookieVO, ElemeCookieDO.class);
        cookieDO.setReceiveDate(new Date(System.currentTimeMillis()));
        cookieDO.setRemainTimes(5);
        cookieDO.setPrivilege(privilege);
        cookieDO.setStatus(ElemeEnum.ELEME_COOKIE_STATUS_ENABLED.getValue());

        return elemeCookieMapper.insert(cookieDO);
    }

    private static final String ELEME_COUPON_URL = "https://h5.ele.me/restapi/marketing/promotion/weixin/oEGLvjh2WrbnkReLy7zex5KgTJ9A";

    @Override
    public ElemeCouponResponseVO getElemeLuckyCoupon(ElemeCouponRequestVO requestVO, PeachUserVO userVO) {
        System.out.println(requestVO.getCouponUrl());

        return null;
    }

}
