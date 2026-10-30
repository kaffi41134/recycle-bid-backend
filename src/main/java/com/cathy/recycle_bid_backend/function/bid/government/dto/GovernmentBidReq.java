package com.cathy.recycle_bid_backend.function.bid.government.dto;

import org.apache.commons.lang3.StringUtils;

import com.cathy.recycle_bid_backend.data.entity.GovernmentBid;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GovernmentBidReq {

    @NotBlank(message = "招標機關代碼不得空白")
    private String unitId;

    @NotBlank(message = "標案案號不得空白")
    private String jobNumber;

    private String unitName;
    private String title;
    private String noticeDate;
    private String noticeType;
    private String category;
    private String tenderMethod;
    private String deadline;
    private String openingTime;
    private String openingLocation;
    private String contactName;
    private String contactPhone;
    private String budgetAmount;
    private String bidBondRequired;
    private String performanceLocation;
    private String sourceUrl;

    public GovernmentBid toEntity() {
        return GovernmentBid.builder()
                .unitId(StringUtils.trimToNull(unitId))
                .jobNumber(StringUtils.trimToNull(jobNumber))
                .unitName(StringUtils.trimToNull(unitName))
                .title(StringUtils.trimToNull(title))
                .noticeDate(StringUtils.trimToNull(noticeDate))
                .noticeType(StringUtils.trimToNull(noticeType))
                .category(StringUtils.trimToNull(category))
                .tenderMethod(StringUtils.trimToNull(tenderMethod))
                .deadline(StringUtils.trimToNull(deadline))
                .openingTime(StringUtils.trimToNull(openingTime))
                .openingLocation(StringUtils.trimToNull(openingLocation))
                .contactName(StringUtils.trimToNull(contactName))
                .contactPhone(StringUtils.trimToNull(contactPhone))
                .budgetAmount(StringUtils.trimToNull(budgetAmount))
                .bidBondRequired(StringUtils.trimToNull(bidBondRequired))
                .performanceLocation(StringUtils.trimToNull(performanceLocation))
                .sourceUrl(StringUtils.trimToNull(sourceUrl))
                .build();
    }

    public GovernmentBid applyTo(GovernmentBid entity) {
        entity.setUnitId(StringUtils.trimToNull(unitId));
        entity.setJobNumber(StringUtils.trimToNull(jobNumber));
        entity.setUnitName(StringUtils.trimToNull(unitName));
        entity.setTitle(StringUtils.trimToNull(title));
        entity.setNoticeDate(StringUtils.trimToNull(noticeDate));
        entity.setNoticeType(StringUtils.trimToNull(noticeType));
        entity.setCategory(StringUtils.trimToNull(category));
        entity.setTenderMethod(StringUtils.trimToNull(tenderMethod));
        entity.setDeadline(StringUtils.trimToNull(deadline));
        entity.setOpeningTime(StringUtils.trimToNull(openingTime));
        entity.setOpeningLocation(StringUtils.trimToNull(openingLocation));
        entity.setContactName(StringUtils.trimToNull(contactName));
        entity.setContactPhone(StringUtils.trimToNull(contactPhone));
        entity.setBudgetAmount(StringUtils.trimToNull(budgetAmount));
        entity.setBidBondRequired(StringUtils.trimToNull(bidBondRequired));
        entity.setPerformanceLocation(StringUtils.trimToNull(performanceLocation));
        entity.setSourceUrl(StringUtils.trimToNull(sourceUrl));

        return entity;
    }
}
