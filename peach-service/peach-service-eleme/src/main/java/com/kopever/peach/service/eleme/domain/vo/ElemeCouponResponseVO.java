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

    private Boolean isSuccess;

    private String nickname;

    private String mobileNumber;

    private BigDecimal amount;

    private String tips;

}
