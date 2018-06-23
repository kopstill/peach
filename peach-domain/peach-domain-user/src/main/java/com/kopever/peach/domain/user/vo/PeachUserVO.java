package com.kopever.peach.domain.user.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class PeachUserVO {

    private BigInteger id;
    private String username;
    private String nickname;
    private String mobileNumber;
    private String secretKey;
    private String email;

}
