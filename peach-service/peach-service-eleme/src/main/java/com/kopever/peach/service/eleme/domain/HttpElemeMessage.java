package com.kopever.peach.service.eleme.domain;

import lombok.Getter;

public enum HttpElemeMessage {

    DEVOTE_COOKIE_FAILED(10601, "Cookie贡献失败，请检查Cookie格式"),
    INVALID_SECRET_KEY(10602, "用户密钥错误"),
    PRIMARY_COOKIE_NOT_FOUND(10603, "用户主Cookie获取失败"),
    COOKIE_NOT_ENOUGH(10604, "Cookie数量不足"),
    NO_EXCESS_COUPON(10605, "来晚了，红包已经被领完了"),
    LUCKY_COUPON_GONE(10606, "大红包已被他人领取"),
    NO_MORE_CHANCE(10607, "悟空，你今天的领取机会已经用完了"),
    CHANCE_SLIPPED(10608, "运气不佳，大红包刚被人领走"),
    LUCKY_MAN(10609, "大红包已进兜"),
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
