package com.kopever.peach.service.eleme.domain;

import lombok.Getter;

public enum HttpElemeMessage {

    DEVOTE_COOKIE_FAILED(10601, "Cookie贡献失败，请检查Cookie格式"),
    INVALID_SECRET_KEY(10602, "用户密钥错误"),
    INVALID_URL(10603, "红包链接无效"),
    COOKIE_NOT_ENOUGH(10604, "Cookie数量不足"),
    NO_EXCESS_COUPON(10605, "来晚了，红包已经被领完了"),
    LUCKY_COUPON_GONE(10606, "大红包已被他人领取"),
    NO_MORE_CHANCE(10607, "悟空，你今天的领取机会已经用完了"),
    COUPON_RECEIVED(10608, "悟空，你已经领过这个红包了"),
    CHANCE_SLIPPED(10609, "运气不佳，大红包刚被人领走"),
    LUCKY_MAN(10610, "大红包已进兜"),
    COUPON_CANCELED(10610, "红包已被取消"),
    EXTRA_OPERATED(10611, "领取失败，有小号点击过这个红包"),
    RECORD_EXCEPTION(10612, "领取记录添加异常"),
    NEXT_LUCKY(10613, "下一个就是最大红包"),
    BIND_EXCEPTION(10614, "手机号绑定失败"),
    UNKNOWN_RETCODE(10615, "领取失败，状态未知"),
    ;

    HttpElemeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Getter
    private int code;

    @Getter
    private String message;

}
