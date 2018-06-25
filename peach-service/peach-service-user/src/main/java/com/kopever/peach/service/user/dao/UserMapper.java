package com.kopever.peach.service.user.dao;

import com.kopever.peach.service.framework.mybatis.BaseMapper;
import com.kopever.peach.service.user.domain.data.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {



}
