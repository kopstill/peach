package com.kopever.peach.service.eleme.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ElemeCouponResponse {

    private String account;
    private Boolean isLucky;
    private Integer luckyStatus;
    private List<PromotionItem> promotionItems;
    private List<PromotionRecord> promotionRecords;

    @Getter
    @Setter
    private static class PromotionItem {

        private Double amount;
        private String expireDate;
        private String[] hongbaoVariety;
        private Integer itemNum;
        private Integer itemType;
        private String name;
        private Integer restaurantId;
        private String restaurantImageHash;
        private String phone;
        private String source;
        private Integer sumCondition;
        private String validityPeriods;

    }

    @Getter
    @Setter
    private static class PromotionRecord {

        private Double amount;
        private Integer createdAt;
        private Boolean isLucky;
        private String snsAvatar;
        private String snsUsername;

    }

    private Integer retCode;
    private Integer themeId;

}
