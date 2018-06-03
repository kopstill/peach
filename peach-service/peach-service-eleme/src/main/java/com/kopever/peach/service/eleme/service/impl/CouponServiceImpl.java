package com.kopever.peach.service.eleme.service.impl;

import com.kopever.peach.common.util.Jackson;
import com.kopever.peach.service.eleme.dao.ElemeCookieMapper;
import com.kopever.peach.service.eleme.domain.data.ElemeCookieDO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.service.CouponService;
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

        ElemeCookieDO cookieDO = Jackson.convert(cookieVO, ElemeCookieDO.class);
        cookieDO.setReceiveDate(new Date(System.currentTimeMillis()));
        cookieDO.setRemainTimes(5);
        cookieDO.setPrivilege(privilege);

        return elemeCookieMapper.insert(cookieDO);
    }

}
