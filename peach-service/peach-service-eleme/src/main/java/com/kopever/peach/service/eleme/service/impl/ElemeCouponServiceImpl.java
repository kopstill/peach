package com.kopever.peach.service.eleme.service.impl;

import com.kopever.peach.common.Dozer;
import com.kopever.peach.common.Jackson;
import com.kopever.peach.common.util.HttpUtil;
import com.kopever.peach.common.util.OkHttpMediaType;
import com.kopever.peach.common.util.OkHttpUtil;
import com.kopever.peach.service.eleme.dao.ElemeCookieMapper;
import com.kopever.peach.service.eleme.dao.ElemeCookieRecordMapper;
import com.kopever.peach.service.eleme.domain.HttpElemeMessage;
import com.kopever.peach.service.eleme.domain.data.ElemeCookieDO;
import com.kopever.peach.service.eleme.domain.data.ElemeCookieRecordDO;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@Service
public class ElemeCouponServiceImpl implements ElemeCouponService {

    private static final Logger logger = LoggerFactory.getLogger(ElemeCouponController.class);

    private final ElemeCookieMapper cookieMapper;

    private final ElemeCookieRecordMapper cookieRecordMapper;

    public ElemeCouponServiceImpl(ElemeCookieMapper cookieMapper, ElemeCookieRecordMapper cookieRecordMapper) {
        this.cookieMapper = cookieMapper;
        this.cookieRecordMapper = cookieRecordMapper;
    }

    @Override
    public int saveElemeCouponCookie(ElemeCookieVO cookieVO, Boolean isPrimary, BigInteger userId) {
        String privilege = StringUtils.join(cookieVO.getPrivilege(), ',');
        cookieVO.setPrivilege(null);

        ElemeCookieDO cookieDO = Dozer.map(cookieVO, ElemeCookieDO.class);
        cookieDO.setPrivilege(privilege);
        cookieDO.setUserId(userId);
        if (isPrimary) {
            cookieDO.setIsPrimary(ElemeEnum.ELEME_COOKIE_PRIMARY.getValue());
        } else {
            cookieDO.setIsPrimary(ElemeEnum.ELEME_COOKIE_SECONDARY.getValue());
        }
        cookieDO.setStatus(ElemeEnum.ELEME_COOKIE_STATUS_ENABLED.getValue());

        return cookieMapper.insert(cookieDO);
    }

    private static final String ELEME_COUPON_URL = "https://h5.ele.me/restapi/marketing/promotion/weixin/%s";

    private static final int ELEME_COUPON_CAPACITY = 15;

    @Override
    public ElemeCouponResponseVO getElemeLuckyCoupon(
            BigInteger userId, String mobileNumber, String couponUrl) throws IOException {
        String decodedCouponUrl = URLDecoder.decode(HtmlUtils.htmlUnescape(couponUrl), "UTF-8").trim();
        logger.info("ElemeCouponServiceImpl.getElemeLuckyCoupon.decodedCouponUrl -> {}", decodedCouponUrl);

        Map<String, String> refParams = HttpUtil.getRefParams(decodedCouponUrl);
        logger.info("ElemeCouponServiceImpl.getElemeLuckyCoupon.refParams -> {}", Jackson.toJson(refParams));

        ElemeAnchor anchor = Jackson.convert(refParams, ElemeAnchor.class);
        logger.info("ElemeCouponServiceImpl.getElemeLuckyCoupon.anchor -> {}", Jackson.toJson(anchor));

        return getElemeLuckyCoupon(userId, mobileNumber, anchor.getSn(), Integer.valueOf(anchor.getLuckyNumber()));
    }

    private ElemeCouponResponseVO getElemeLuckyCoupon(BigInteger userId, String mobileNumber, String sn, int luckyNumber) throws IOException {
        ElemeCookieDO primaryCookie = getUserPrimaryCookie(userId);
        if (primaryCookie == null) {
            return new ElemeCouponResponseVO().setIsSuccess(false).
                    setTips(HttpElemeMessage.PRIMARY_COOKIE_NOT_FOUND.getMessage());
        }

        return recursiveProcessElemeCoupon(primaryCookie, mobileNumber, sn, luckyNumber);
    }

    private ElemeCouponResponseVO recursiveProcessElemeCoupon(ElemeCookieDO primaryCookie, String mobileNumber, String sn, int luckyNumber) throws IOException {
        int capacity = calculateCookieCapacity(luckyNumber);
        List<ElemeCookieDO> secondaryCookies = cookieMapper.getSecondaryElemeCookie(primaryCookie.getId(), capacity);
        if (secondaryCookies == null || secondaryCookies.size() < luckyNumber - 1) {
            return new ElemeCouponResponseVO().setIsSuccess(false).setTips(HttpElemeMessage.COOKIE_NOT_ENOUGH.getMessage());
        }

        for (ElemeCookieDO cookieDO : secondaryCookies) {
            ElemeCouponRequest couponRequest = new ElemeCouponRequest();
            couponRequest.setGroupSn(sn);
            couponRequest.setSign(cookieDO.getElemeKey());
            couponRequest.setWeixinAvatar(cookieDO.getAvatar());
            couponRequest.setWeixinUsername(cookieDO.getNickname());

            String result = OkHttpUtil.post(String.format(ELEME_COUPON_URL, cookieDO.getOpenid()), Jackson.toJson(couponRequest), OkHttpMediaType.TEXT);
            ElemeCouponResponse couponResponse = Jackson.fromJson(result, ElemeCouponResponse.class);

            int retCode = couponResponse.getRetCode();
            List<ElemeCouponResponse.PromotionRecord> promotionRecords = couponResponse.getPromotionRecords();
            if (retCode == 5) {
                continue;
            }
            if (promotionRecords != null && promotionRecords.size() == ELEME_COUPON_CAPACITY) {
                if (cookieDO.getNickname().equals(promotionRecords.get(promotionRecords.size() - 1).getSnsUsername())) {
                    addElemeCookieRecord(cookieDO.getId(), false);
                }
                return new ElemeCouponResponseVO().setIsSuccess(false).setTips(HttpElemeMessage.NO_EXCESS_COUPON.getMessage());
            } else if (promotionRecords != null && promotionRecords.size() >= luckyNumber) {
                isCookieRecord(cookieDO, promotionRecords);
                return new ElemeCouponResponseVO().setIsSuccess(false).setTips(HttpElemeMessage.LUCKY_COUPON_GONE.getMessage());
            } else {
                isCookieRecord(cookieDO, promotionRecords);
                if (promotionRecords != null && promotionRecords.size() == luckyNumber - 1) {
                    return getTheLuckyCoupon(primaryCookie, mobileNumber, sn, luckyNumber);
                }
            }
        }

        return recursiveProcessElemeCoupon(primaryCookie, mobileNumber, sn, luckyNumber);
    }

    private void isCookieRecord(ElemeCookieDO cookieDO, List<ElemeCouponResponse.PromotionRecord> promotionRecords) {
        boolean isRequested = false;
        for (ElemeCouponResponse.PromotionRecord promotionRecord : promotionRecords) {
            if (promotionRecord.getSnsUsername().equals(cookieDO.getNickname())) {
                isRequested = true;
            }
        }
        if (!isRequested) {
            addElemeCookieRecord(cookieDO.getId(), false);
        }
    }

    private ElemeCouponResponseVO getTheLuckyCoupon(ElemeCookieDO primaryCookie, String mobileNumber, String sn, int luckyNumber) throws IOException {
        ElemeCouponRequest couponRequest = new ElemeCouponRequest();
        couponRequest.setGroupSn(sn);
        couponRequest.setSign(primaryCookie.getElemeKey());
        couponRequest.setWeixinAvatar(primaryCookie.getAvatar());
        couponRequest.setWeixinUsername(primaryCookie.getNickname());

        String result = OkHttpUtil.post(String.format(ELEME_COUPON_URL, primaryCookie.getOpenid()), Jackson.toJson(couponRequest), OkHttpMediaType.TEXT);
        ElemeCouponResponse couponResponse = Jackson.fromJson(result, ElemeCouponResponse.class);

        if (couponResponse.getRetCode() == 5) {
            return new ElemeCouponResponseVO().setIsSuccess(false).setTips(HttpElemeMessage.NO_MORE_CHANCE.getMessage());
        } else if (couponResponse.getPromotionRecords() != null && couponResponse.getPromotionRecords().size() == luckyNumber) {
            addElemeCookieRecord(primaryCookie.getId(), true);
            return new ElemeCouponResponseVO().setIsSuccess(true)
                    .setTips(HttpElemeMessage.LUCKY_MAN.getMessage())
                    .setAmount(new BigDecimal(couponResponse.getAccount()))
                    .setMobileNumber(mobileNumber)
                    .setNickname(primaryCookie.getNickname());
        } else {
            addElemeCookieRecord(primaryCookie.getId(), false);
            return new ElemeCouponResponseVO().setIsSuccess(false).setTips(HttpElemeMessage.CHANCE_SLIPPED.getMessage());
        }
    }

    private void addElemeCookieRecord(BigInteger cookieId, boolean isLucky) {
        ElemeCookieRecordDO recordDO = new ElemeCookieRecordDO();
        recordDO.setCookieId(cookieId);
        if (isLucky) {
            recordDO.setIsLucky(ElemeEnum.ELEME_LUCKY_MAN.getValue());
        } else {
            recordDO.setIsLucky(ElemeEnum.ELEME_POOR_MAN.getValue());
        }

        cookieRecordMapper.insert(recordDO);
    }

    private int calculateCookieCapacity(int number) {
        return (int) (number + number * 0.75);
    }

    private ElemeCookieDO getUserPrimaryCookie(BigInteger userId) {
        ElemeCookieDO query = new ElemeCookieDO();
        query.setUserId(userId);
        query.setIsPrimary(ElemeEnum.ELEME_COOKIE_PRIMARY.getValue());
        query.setStatus(ElemeEnum.ELEME_COOKIE_STATUS_ENABLED.getValue());

        return cookieMapper.get(query);
    }

}
