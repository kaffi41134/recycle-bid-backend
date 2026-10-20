package com.cathy.recycle_bid_backend.function.bid.dto;

public record BidNoticeRes(

        // 招標機關代碼
        String unitId,

        // 標案案號
        String jobNumber,

        // 公告日期
        String noticeDate,

        // 公告類型
        String noticeType,

        // 標案名稱
        String title,

        // 公告檔名
        String filename) {
}
