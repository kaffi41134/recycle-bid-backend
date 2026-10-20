
package com.cathy.recycle_bid_backend.function.bid.dto;

public record BidImportRes(

        // 招標機關代碼
        String unitId,

        // 標案案號
        String jobNumber,

        // 招標機關名稱
        String unitName,

        // 標案名稱
        String title,

        // 公告日期
        String noticeDate,

        // 公告類型
        String noticeType,

        // 標的分類
        String category,

        // 招標方式
        String tenderMethod,

        // 截止投標時間
        String deadline,

        // 開標時間
        String openingTime,

        // 開標地點
        String openingLocation,

        // 機關聯絡人
        String contactName,

        // 機關聯絡電話
        String contactPhone,

        // 預算金額
        String budgetAmount,

        // 是否須繳納押標金
        String bidBondRequired,

        // 履約地點
        String performanceLocation,

        // 政府標案公告網址
        String sourceUrl

) {
}
