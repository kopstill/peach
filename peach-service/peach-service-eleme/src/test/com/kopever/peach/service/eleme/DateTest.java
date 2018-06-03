package com.kopever.peach.service.eleme;

import org.junit.Test;

import java.sql.Date;

public class DateTest {

    @Test
    public void testDate() {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }

}
