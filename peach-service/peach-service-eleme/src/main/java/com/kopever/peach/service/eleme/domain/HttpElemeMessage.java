package com.kopever.peach.service.eleme.domain;

import lombok.Getter;

public enum HttpElemeMessage {

    DEVOTE_COOKIE_FAILED(10601, "Cookie贡献失败，请检查Cookie格式"),
    COOKIE_EXISTED(10602, "该Cookie已存在，请重新贡献"),
    INVALID_SECRET_KEY(10603, "用户密钥错误"),
    INVALID_URL(10604, "红包链接无效"),
    COOKIE_NOT_ENOUGH(10605, "Cookie数量不足"),
    NO_EXCESS_COUPON(10606, "来晚了，红包已经被领完了"),
    LUCKY_COUPON_GONE(10607, "大红包已被他人领取"),
    NO_MORE_CHANCE(10608, "悟空，你今天的领取机会已经用完了"),
    COUPON_RECEIVED(10609, "悟空，你已经领过这个红包了"),
    CHANCE_SLIPPED(10610, "运气不佳，大红包刚被人领走"),
    LUCKY_MAN(10611, "大红包已进兜"),
    COUPON_CANCELED(10612, "红包已被取消"),
    EXTRA_OPERATED(10613, "领取失败，有小号点击过这个红包"),
    RECORD_EXCEPTION(10614, "领取记录添加异常"),
    NEXT_LUCKY(10615, "下一个就是最大红包"),
    BIND_EXCEPTION(10616, "手机号绑定失败"),
    UNKNOWN_RETCODE(10617, "领取失败，状态未知"),
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
