package com.kopever.peach.service.framework.mybatis;

import java.util.List;

public interface BaseMapper<T> {

    T get(T clazz);

    int insert(T clazz);

    int delete(T clazz);

    int update(T clazz);

    List<T> list(T clazz);

    long count(T clazz);

}
