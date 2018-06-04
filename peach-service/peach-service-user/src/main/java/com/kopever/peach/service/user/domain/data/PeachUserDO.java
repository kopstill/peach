package com.kopever.peach.service.user.domain.data;

import com.kopever.peach.service.framework.domain.data.BaseDO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeachUserDO extends BaseDO {

    private String username;
    private String nickname;
    private String mobileNumber;
    private String password;
    private String secretKey;
    private String email;

}
