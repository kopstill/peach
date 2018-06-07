package com.kopever.peach.service.eleme.dao;

import com.kopever.peach.service.eleme.domain.data.ElemeCookieDO;
import com.kopever.peach.service.framework.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface ElemeCookieMapper extends BaseMapper<ElemeCookieDO> {

    List<ElemeCookieDO> getSecondaryElemeCookie(@Param("primaryCookieId") BigInteger primaryCookieId, @Param("capacity") int capacity);

}
