package com.kopever.peach.service.eleme.service;

import com.kopever.peach.service.eleme.domain.vo.ElemeCookieVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponRequestVO;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponResponseVO;
import com.kopever.peach.service.user.domain.vo.PeachUserVO;

public interface CouponService {

    int saveElemeCouponCookie(ElemeCookieVO elemeCookieVO);

    ElemeCouponResponseVO getElemeLuckyCoupon(ElemeCouponRequestVO requestVO, PeachUserVO userVO);

}
