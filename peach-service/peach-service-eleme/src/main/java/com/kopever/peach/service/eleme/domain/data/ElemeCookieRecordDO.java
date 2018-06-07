package com.kopever.peach.service.eleme.domain.data;

import com.kopever.peach.service.framework.domain.data.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Date;

@Getter
@Setter
public class ElemeCookieRecordDO extends BaseDO {

    private BigInteger cookieId;
    private Date useDate;
    private Integer isLucky;

}
