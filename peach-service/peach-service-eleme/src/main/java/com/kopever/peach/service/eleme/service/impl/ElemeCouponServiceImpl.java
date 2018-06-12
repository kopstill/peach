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
    public ElemeCookieDO getElemeCouponCookieByOpenid(String openid) {
        ElemeCookieDO cookieDO = new ElemeCookieDO();
        cookieDO.setOpenid(openid);

        return cookieMapper.get(cookieDO);
    }

    @Override
    public int saveElemeCouponCookie(String origin, ElemeCookieVO cookieVO, BigInteger userId) {
        String privilege = StringUtils.join(cookieVO.getPrivilege(), ',').intern();
        cookieVO.setPrivilege(null);

        ElemeCookieDO cookieDO = Dozer.map(cookieVO, ElemeCookieDO.class);
        cookieDO.setPrivilege(privilege);
        cookieDO.setUserId(userId);
        cookieDO.setOrigin(origin);
        cookieDO.setStatus(ElemeEnum.ELEME_COOKIE_STATUS_ENABLED.getValue());

        return cookieMapper.insert(cookieDO);
    }


    private static final String ELEME_COUPON_URL = "https://h5.ele.me/restapi/marketing/promotion/weixin/%s";

    @Override
    public ElemeCouponResponseVO getElemeLuckyCoupon(String mobileNumber, String couponUrl) throws IOException {
        String decodedCouponUrl = URLDecoder.decode(HtmlUtils.htmlUnescape(couponUrl), "UTF-8").trim();
        logger.info("ElemeCouponServiceImpl.getElemeLuckyCoupon.decodedCouponUrl -> {}", decodedCouponUrl);

        Map<String, String> refParams = HttpUtil.getRefParams(decodedCouponUrl);
        ElemeAnchor anchor = Jackson.convert(refParams, ElemeAnchor.class);
        if (anchor == null || StringUtils.isAnyBlank(anchor.getSn(), anchor.getLuckyNumber())) {
            return new ElemeCouponResponseVO().setIsSuccess(false).setTips(HttpElemeMessage.INVALID_URL.getMessage());
        }

        return getElemeLuckyCoupon(mobileNumber, anchor.getSn(), Integer.valueOf(anchor.getLuckyNumber()));
    }

    private ElemeCouponResponseVO getElemeLuckyCoupon(String mobileNumber, String sn, int luckyNumber) throws IOException {
        ElemeCouponResponseVO responseVO = new ElemeCouponResponseVO().setIsSuccess(false);

        int leastSize = StringUtils.isEmpty(mobileNumber) ? luckyNumber - 1 : luckyNumber;
        List<ElemeCookieDO> availableCookies = cookieMapper.getAvailableElemeCookie(leastSize);
        if (availableCookies == null || availableCookies.size() < leastSize) {
            return responseVO.setTips(HttpElemeMessage.COOKIE_NOT_ENOUGH.getMessage());
        }

        for (ElemeCookieDO cookieDO : availableCookies) {
            ElemeCouponRequest couponRequest = new ElemeCouponRequest();
            couponRequest.setGroupSn(sn);
            couponRequest.setSign(cookieDO.getElemeKey());
            couponRequest.setWeixinAvatar(cookieDO.getAvatar());
            couponRequest.setWeixinUsername(cookieDO.getNickname());

            String result = OkHttpUtil.post(String.format(ELEME_COUPON_URL, cookieDO.getOpenid()), Jackson.toJson(couponRequest), OkHttpMediaType.TEXT);
            logger.info("ElemeCouponServiceImpl.getElemeLuckyCoupon.result -> {}", result);

            ElemeCouponResponse couponResponse = Jackson.fromJson(result, ElemeCouponResponse.class);
            int retCode = couponResponse.getRetCode();
            List<ElemeCouponResponse.PromotionRecord> promotionRecords = couponResponse.getPromotionRecords();
            int currentSize = promotionRecords.size();
            BigDecimal amount = promotionRecords.get(currentSize - 1).getAmount();
            String account = couponResponse.getAccount();
            BigInteger cookieId = cookieDO.getId();
            if (couponResponse.getLuckyStatus() == 3) {
                if (retCode == 3 || retCode == 4) {
                    if (addElemeCookieRecord(cookieId, account, amount, sn, couponResponse.getIsLucky())) {
                        return responseVO.setTips(HttpElemeMessage.RECORD_EXCEPTION.getMessage());
                    }
                }
                return responseVO.setTips(HttpElemeMessage.LUCKY_COUPON_GONE.getMessage());
            }

            if (retCode == 1) {
                return responseVO.setTips(HttpElemeMessage.NO_EXCESS_COUPON.getMessage());
            } else if (retCode == 2) {
                return responseVO.setTips(HttpElemeMessage.EXTRA_OPERATED.getMessage());
            } else if (retCode == 3 || retCode == 4) {
                if (addElemeCookieRecord(cookieId, account, amount, sn, false)) {
                    return responseVO.setTips(HttpElemeMessage.RECORD_EXCEPTION.getMessage());
                }
            } else if (retCode == 5) {
                return responseVO.setTips(HttpElemeMessage.EXTRA_OPERATED.getMessage());
            } else if (retCode == 6) {
                return responseVO.setTips(HttpElemeMessage.COUPON_CANCELED.getMessage());
            } else {
                return responseVO.setTips(HttpElemeMessage.UNKNOWN_RETCODE.getMessage());
            }

            if (currentSize == luckyNumber - 1) {
                break;
            }
        }

        if (StringUtils.isBlank(mobileNumber)) {
            return responseVO.setIsSuccess(true).setTips(HttpElemeMessage.NEXT_LUCKY.getMessage());
        }

        return getTheLuckyCoupon(availableCookies.get(luckyNumber - 1), mobileNumber, sn);
    }

    private ElemeCouponResponseVO getTheLuckyCoupon(ElemeCookieDO targetCookie, String mobileNumber, String sn) throws IOException {
        ElemeCouponResponseVO responseVO = new ElemeCouponResponseVO().setIsSuccess(false);
        if (bindAccountToMobileNumber(targetCookie.getOpenid(), targetCookie.getElemeKey(), mobileNumber)) {
            return responseVO.setTips(HttpElemeMessage.BIND_EXCEPTION.getMessage());
        }

        ElemeCouponRequest couponRequest = new ElemeCouponRequest();
        couponRequest.setGroupSn(sn);
        couponRequest.setSign(targetCookie.getElemeKey());
        couponRequest.setWeixinAvatar(targetCookie.getAvatar());
        couponRequest.setWeixinUsername(targetCookie.getNickname());

        String result = OkHttpUtil.post(String.format(ELEME_COUPON_URL, targetCookie.getOpenid()), Jackson.toJson(couponRequest), OkHttpMediaType.TEXT);
        logger.info("ElemeCouponServiceImpl.getTheLuckyCoupon.result -> {}", result);

        ElemeCouponResponse couponResponse = Jackson.fromJson(result, ElemeCouponResponse.class);
        List<ElemeCouponResponse.PromotionRecord> promotionRecords = couponResponse.getPromotionRecords();
        BigInteger cookieId = targetCookie.getId();
        String account = couponResponse.getAccount();
        BigDecimal amount = promotionRecords.get(promotionRecords.size() - 1).getAmount();

        if (bindAccountToMobileNumber(targetCookie.getOpenid(), targetCookie.getElemeKey(), RESET_MOBILE_NUMBER)) {
            logger.warn("ElemeCouponServiceImpl.getTheLuckyCoupon.resetMobileNumberFailed" +
                    " -> openid: {}, mobile_number: {}, serial_number: {}", targetCookie.getOpenid(), mobileNumber, sn);
        }

        int retCode = couponResponse.getRetCode();
        if (couponResponse.getIsLucky()) {
            if (retCode == 3 || retCode == 4) {
                if (addElemeCookieRecord(cookieId, account, amount, sn, true)) {
                    return responseVO.setTips(HttpElemeMessage.RECORD_EXCEPTION.getMessage());
                }
            }
            return responseVO.setIsSuccess(true)
                    .setTips(HttpElemeMessage.LUCKY_MAN.getMessage())
                    .setAmount(new BigDecimal(account))
                    .setMobileNumber(mobileNumber)
                    .setNickname(targetCookie.getNickname());
        } else {
            if (retCode == 1) {
                return responseVO.setTips(HttpElemeMessage.NO_EXCESS_COUPON.getMessage());
            } else if (retCode == 2) {
                return responseVO.setTips(HttpElemeMessage.COUPON_RECEIVED.getMessage());
            } else if (retCode == 3 || retCode == 4) {
                if (addElemeCookieRecord(cookieId, account, amount, sn, false)) {
                    return responseVO.setTips(HttpElemeMessage.RECORD_EXCEPTION.getMessage());
                }
                return responseVO.setTips(HttpElemeMessage.CHANCE_SLIPPED.getMessage());
            } else if (retCode == 5) {
                return responseVO.setTips(HttpElemeMessage.NO_MORE_CHANCE.getMessage());
            } else {
                return responseVO.setTips(HttpElemeMessage.UNKNOWN_RETCODE.getMessage());
            }
        }
    }

    private static final String RESET_MOBILE_NUMBER = "12345678901";

    private static final String ELEME_BIND_URL = "https://h5.ele.me/restapi/v1/weixin/%s/phone";

    private boolean bindAccountToMobileNumber(String openid, String sign, String mobileNumber) throws IOException {
        ElemeBindRequest bindRequest = new ElemeBindRequest();
        bindRequest.setSign(sign);
        bindRequest.setPhone(mobileNumber);

        String bindRequestContent = Jackson.toJson(bindRequest);
        logger.info("ElemeCouponServiceImpl.bindAccountToMobileNumber.bindRequestContent -> {}", bindRequestContent);

        String result = OkHttpUtil.put(String.format(ELEME_BIND_URL, openid), bindRequestContent, OkHttpMediaType.TEXT);
        logger.info("ElemeCouponServiceImpl.bindAccountToMobileNumber.result -> {}", StringUtils.isEmpty(result) ? "\"\" means success" : result);

        return !"".equals(result);
    }

    private boolean addElemeCookieRecord(BigInteger cookieId, String account, BigDecimal amount, String serialNumber, boolean isLucky) {
        ElemeCookieRecordDO recordDO = new ElemeCookieRecordDO();
        recordDO.setCookieId(cookieId);
        recordDO.setAccount(account);
        recordDO.setAmount(amount);
        recordDO.setSerialNumber(serialNumber);
        if (isLucky) {
            recordDO.setIsLucky(ElemeEnum.ELEME_LUCKY_MAN.getValue());
        } else {
            recordDO.setIsLucky(ElemeEnum.ELEME_POOR_MAN.getValue());
        }

        return cookieRecordMapper.insert(recordDO) != 1;
    }

}
