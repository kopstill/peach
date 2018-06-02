package com.kopever.peach.service.eleme.service.impl;

import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.service.CouponService;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {

    @Override
    public void saveElemeCouponCookie(ElemeCookieVO elemeCookieVO) {
        System.out.println(elemeCookieVO);
    }

}
