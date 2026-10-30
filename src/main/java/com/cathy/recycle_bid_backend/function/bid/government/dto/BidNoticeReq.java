package com.cathy.recycle_bid_backend.function.bid.government.dto;

import jakarta.validation.constraints.NotBlank;

public record BidNoticeReq(
        @NotBlank(message = "機關代碼不可為空") String unitId,

        @NotBlank(message = "標案案號不可為空") String jobNumber) {
}
