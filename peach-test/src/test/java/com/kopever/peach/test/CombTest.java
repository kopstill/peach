package com.kopever.peach.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lullaby on 2018/5/19
 */
public class CombTest {

    @Test
    public void testThreeInFive() {
        int[] des = {1, 2, 3, 4, 5};
        int len = 3;

        List<int[]> list = combo(des, 0, new int[len], 0, new ArrayList<>());

        int result = 0;
        for (int[] arr : list) {

            int sec = 1;
            for (int val : arr) {
                sec *= val;
            }
            System.out.println(Arrays.toString(arr) + " = " + sec);

            result += sec;
        }

        System.out.println(result);
    }

    private List<int[]> combo(int[] arr, int startIndex, int[] res, int refIndex, List<int[]> result) {
        int len = res.length;
        int count = refIndex + 1;

        if (count > len) {
            result.add(res.clone());
            return result;
        }

        for (int i = startIndex; i < arr.length + count - len; i++) {
            res[refIndex] = arr[i];
            combo(arr, i + 1, res, refIndex + 1, result);
        }

        return result;
    }

}
