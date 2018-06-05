package com.kopever.peach.service.eleme.domain.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created by Lullaby on 2018/5/31
 */
@Getter
@Setter
public class ElemeCouponRequestVO {

    @NotBlank
    private String couponUrl;

    @NotBlank
    private String username;

    @NotBlank
    private String secretKey;

}
