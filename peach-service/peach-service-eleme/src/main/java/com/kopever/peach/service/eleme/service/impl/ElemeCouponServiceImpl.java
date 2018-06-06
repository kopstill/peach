package com.kopever.peach.service.eleme.service.impl;

import com.kopever.peach.common.Dozer;
import com.kopever.peach.common.Jackson;
import com.kopever.peach.common.util.HttpUtil;
import com.kopever.peach.common.util.OkHttpMediaType;
import com.kopever.peach.common.util.OkHttpUtil;
import com.kopever.peach.service.eleme.dao.ElemeCookieMapper;
import com.kopever.peach.service.eleme.domain.data.ElemeCookieDO;
import com.kopever.peach.service.eleme.domain.data.ElemeEnum;
import com.kopever.peach.service.eleme.domain.vo.*;
import com.kopever.peach.service.eleme.service.ElemeCouponService;
import com.kopever.peach.service.eleme.web.ElemeCouponController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.Map;

@Service
public class ElemeCouponServiceImpl implements ElemeCouponService {

    private static final Logger logger = LoggerFactory.getLogger(ElemeCouponController.class);

    private final ElemeCookieMapper cookieMapper;

    public ElemeCouponServiceImpl(ElemeCookieMapper cookieMapper) {
        this.cookieMapper = cookieMapper;
    }

    @Override
    public int saveElemeCouponCookie(ElemeCookieVO cookieVO, Boolean isPrimary, BigInteger userId) {
        String privilege = StringUtils.join(cookieVO.getPrivilege(), ',');
        cookieVO.setPrivilege(null);

        ElemeCookieDO cookieDO = Dozer.map(cookieVO, ElemeCookieDO.class);
        cookieDO.setPrivilege(privilege);
        cookieDO.setUserId(userId);
        if (isPrimary) {
            cookieDO.setCookieType(ElemeEnum.ELEME_COOKIE_TYPE_PRIMARY.getValue());
        } else {
            cookieDO.setCookieType(ElemeEnum.ELEME_COOKIE_TYPE_SECONDARY.getValue());
        }
        cookieDO.setStatus(ElemeEnum.ELEME_COOKIE_STATUS_ENABLED.getValue());

        return cookieMapper.insert(cookieDO);
    }

    private static final String ELEME_COUPON_URL = "https://h5.ele.me/restapi/marketing/promotion/weixin/oEGLvjh2WrbnkReLy7zex5KgTJ9A";

    @Override
    public ElemeCouponResponseVO getElemeLuckyCoupon(
            BigInteger userId, String couponUrl) throws IOException {
        String decodedCouponUrl = URLDecoder.decode(HtmlUtils.htmlUnescape(couponUrl), "UTF-8").trim();
        logger.info("ElemeCouponServiceImpl.getElemeLuckyCoupon.decodedCouponUrl -> {}", decodedCouponUrl);

        Map<String, String> refParams = HttpUtil.getRefParams(decodedCouponUrl);
        logger.info("ElemeCouponServiceImpl.getElemeLuckyCoupon.refParams -> {}", Jackson.toJson(refParams));

        ElemeAnchor anchor = Jackson.convert(refParams, ElemeAnchor.class);
        logger.info("ElemeCouponServiceImpl.getElemeLuckyCoupon.anchor -> {}", Jackson.toJson(anchor));

        return getElemeLuckyCoupon(userId, anchor.getSn(), anchor.getLuckyNumber());
    }

    private ElemeCouponResponseVO getElemeLuckyCoupon(BigInteger userId, String sn, String luckyNumber) throws IOException {
        ElemeCouponRequest couponRequest = new ElemeCouponRequest();
        couponRequest.setGroupSn(sn);
        couponRequest.setSign("2d5ced3d20546b44fda6fa64f5b1044e");

        String result = OkHttpUtil.post(ELEME_COUPON_URL, Jackson.toJson(couponRequest), OkHttpMediaType.TEXT);
        logger.info("ElemeCouponServiceImpl.getElemeLuckyCoupon.result -> {}", result);

        ElemeCouponResponse couponResponse = Jackson.fromJson(result, ElemeCouponResponse.class);
        return null;
    }

}
