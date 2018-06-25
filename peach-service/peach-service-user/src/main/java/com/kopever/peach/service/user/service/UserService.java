package com.kopever.peach.service.user.service;

import com.kopever.peach.service.user.domain.data.UserDO;

public interface UserService {

    UserDO getUserByUsername(String username);

}
