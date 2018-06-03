package com.kopever.peach.service.framework.domain.data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
public class BaseDO {

    private BigInteger id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

}
