package com.kopever.peach.test;

import org.junit.Test;

public class PrimeNumberTest {

    @Test
    public void testPrimeNumber() {
        for (int i = 1; i < 100; i++) {
            boolean flag = true;

            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                System.out.println(i);
            }
        }
    }

}
