package com.kopever.peach.service.user.service;

import com.kopever.peach.service.user.domain.data.PeachUserDO;

public interface UserService {

    PeachUserDO getUserByMobileNumber(String mobileNumber);

}
