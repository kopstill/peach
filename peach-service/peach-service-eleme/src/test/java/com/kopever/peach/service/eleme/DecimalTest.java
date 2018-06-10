package com.kopever.peach.service.eleme;

import com.kopever.peach.common.Jackson;
import com.kopever.peach.service.eleme.domain.vo.ElemeCouponResponse;
import org.junit.Test;

public class DecimalTest {

    @Test
    public void test() {
        String json = "{\n" +
                "    \"account\":\"13544446193\",\n" +
                "    \"is_lucky\":false,\n" +
                "    \"lucky_status\":2,\n" +
                "    \"promotion_items\":[\n" +
                "        {\n" +
                "            \"amount\":2,\n" +
                "            \"expire_date\":\"2018-06-08\",\n" +
                "            \"hongbao_variety\":[\n" +
                "                \"全品类\"\n" +
                "            ],\n" +
                "            \"item_type\":1,\n" +
                "            \"name\":\"拼手气红包\",\n" +
                "            \"phone\":\"13544446193\",\n" +
                "            \"source\":\"weixin_share_hongbao\",\n" +
                "            \"sum_condition\":30,\n" +
                "            \"validity_periods\":\"2018-06-08到期\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\":4,\n" +
                "            \"expire_date\":\"2018-06-11\",\n" +
                "            \"hongbao_variety\":[\n" +
                "                \"全品类\"\n" +
                "            ],\n" +
                "            \"item_type\":1,\n" +
                "            \"name\":\"帮买帮送专享红包\",\n" +
                "            \"phone\":\"13544446193\",\n" +
                "            \"source\":\"lpd_tcs\",\n" +
                "            \"sum_condition\":10,\n" +
                "            \"validity_periods\":\"2018-06-11到期\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"promotion_records\":[\n" +
                "        {\n" +
                "            \"amount\":2.2,\n" +
                "            \"created_at\":1528290196,\n" +
                "            \"is_lucky\":false,\n" +
                "            \"sns_avatar\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM4dSH3iaEZE7pUue4pF1s0vakEiaiahgLoB55KwPzn703RAss2QoYQh9A3j9icrzNp6TQGYHc0K8SNYJQ/132\",\n" +
                "            \"sns_username\":\"博佳覺得您說得都對\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\":2.8,\n" +
                "            \"created_at\":1528292990,\n" +
                "            \"is_lucky\":false,\n" +
                "            \"sns_avatar\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI9B4IQO1L72agBficwOMGtj8tvqCJBk7SBaluVrB4YWibMeOIAAN2G1q2O7TAFEJkRCaCDBhWy5WhA/132\",\n" +
                "            \"sns_username\":\"向南三步\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\":2.2,\n" +
                "            \"created_at\":1528293855,\n" +
                "            \"is_lucky\":false,\n" +
                "            \"sns_avatar\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKS4a1IwJOrnMuEC8AEsskTdaO1qXBt5kcoCjxDCyOOeLmOFa4Ig1s55VQsOpDic2ak0nkO17dclaQ/132\",\n" +
                "            \"sns_username\":\"?\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\":2.8,\n" +
                "            \"created_at\":1528301126,\n" +
                "            \"is_lucky\":false,\n" +
                "            \"sns_avatar\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/B2lbKwd3ufL4HWibmqIvsh5hHhETTUm1v1GKJWQa4l7iaP1vvJ10eEtGIDiaA3IlZmD3AyHT5qHm4icgYGPIyH8k5Q/132\",\n" +
                "            \"sns_username\":\"kopever\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\":3,\n" +
                "            \"created_at\":1528301426,\n" +
                "            \"is_lucky\":false,\n" +
                "            \"sns_avatar\":\"\",\n" +
                "            \"sns_username\":\"130****3690\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"amount\":2,\n" +
                "            \"created_at\":1528301651,\n" +
                "            \"is_lucky\":false,\n" +
                "            \"sns_avatar\":\"\",\n" +
                "            \"sns_username\":\"135****6193\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"ret_code\":4,\n" +
                "    \"theme_id\":1889\n" +
                "}";
        ElemeCouponResponse couponResponse = Jackson.fromJson(json, ElemeCouponResponse.class);
        System.out.println(couponResponse);
    }

}
