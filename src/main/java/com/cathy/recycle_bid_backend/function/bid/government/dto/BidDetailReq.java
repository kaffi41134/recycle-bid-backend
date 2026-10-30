package com.cathy.recycle_bid_backend.function.bid.government.dto;

import jakarta.validation.constraints.NotBlank;

public record BidDetailReq(

        // 招標機關代碼
        @NotBlank String unitId,

        // 標案案號
        @NotBlank String jobNumber,

        // 公告日期（yyyyMMdd）
        @NotBlank String noticeDate,

        // 公告檔名
        @NotBlank String filename

) {
}
