package com.kopever.peach.service.user.service.impl;

import com.kopever.peach.service.user.dao.UserMapper;
import com.kopever.peach.service.user.domain.data.UserDO;
import com.kopever.peach.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserDO getUserByUsername(String username) {
        UserDO userDO = new UserDO();
        userDO.setUsername(username);

        return userMapper.get(userDO);
    }

}
