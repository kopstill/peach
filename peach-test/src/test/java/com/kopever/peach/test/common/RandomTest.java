package com.kopever.peach.test.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class RandomTest {

    @Test
    public void testRandomUtil() {
        System.out.println(RandomStringUtils.random(32));
        System.out.println(RandomStringUtils.randomAlphabetic(32));
        System.out.println(RandomStringUtils.randomAlphanumeric(32));
        System.out.println(RandomStringUtils.randomAscii(32));
    }

}
