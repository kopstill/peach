package com.kopever.peach.service.eleme.domain.data;

import com.kopever.peach.service.framework.domain.data.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ElemeCookieDO extends BaseDO {

    private Date receiveDate;
    private Integer remainTimes;

    private String elemeKey;
    private String language;
    private String name;
    private String avatar;

    private String city;
    private String country;
    private String headimgurl;
    private String nickname;
    private String openid;
    private String privilege;
    private String province;
    private Integer sex;
    private String unionid;

    private Integer status;

}
