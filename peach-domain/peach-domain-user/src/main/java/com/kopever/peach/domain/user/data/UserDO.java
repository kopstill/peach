package com.kopever.peach.domain.user.data;

import com.kopever.peach.domain.data.BaseDO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDO extends BaseDO {

    private String username;
    private String nickname;
    private String mobileNumber;
    private String password;
    private String secretKey;
    private String email;

}
