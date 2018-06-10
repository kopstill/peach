package com.kopever.peach.service.eleme.domain.data;

import lombok.Getter;

public enum ElemeEnum {

    ELEME_COOKIE_STATUS_ENABLED(1, "有效"),
    ELEME_COOKIE_STATUS_DISABLED(0, "无效"),

    ELEME_LUCKY_MAN(1, "人生赢家"),
    ELEME_POOR_MAN(0, "重头再来"),;

    ElemeEnum(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    @Getter
    private int value;

    @Getter
    private String comment;

}
