package com.kopever.peach.service.eleme.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Lullaby on 2018/5/31
 */
@Getter
@Setter
public class ElemeCouponResponseVO {

    private String nickname;

    private String phoneNumber;

    private BigDecimal amount;

}
