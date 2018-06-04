package com.kopever.peach.common;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

public class Dozer {

    public static <T> T map(Object source, Class<T> clazz) {
        if (source == null) return null;

        return DozerInstance.INSTANCE.getInstance().map(source, clazz);
    }

    public static <T> void copy(Object source, T target) {
        if (source == null) return;

        DozerInstance.INSTANCE.getInstance().map(source, target);
    }

}

enum DozerInstance {

    INSTANCE;

    private Mapper mapper;

    DozerInstance() {
        mapper = DozerBeanMapperBuilder.buildDefault();
    }

    public Mapper getInstance() {
        return mapper;
    }

}
