package com.cathy.recycle_bid_backend.function.bid.dto;

import java.time.LocalDateTime;

import com.cathy.recycle_bid_backend.data.entity.GovernmentBid;

import lombok.Data;

@Data
public class GovernmentBidRes {

    private Long id;
    private String unitId;
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
    private LocalDateTime createdAt;

    public static GovernmentBidRes of(GovernmentBid entity) {
        GovernmentBidRes res = new GovernmentBidRes();

        res.setId(entity.getId());
        res.setUnitId(entity.getUnitId());
        res.setJobNumber(entity.getJobNumber());
        res.setUnitName(entity.getUnitName());
        res.setTitle(entity.getTitle());
        res.setNoticeDate(entity.getNoticeDate());
        res.setNoticeType(entity.getNoticeType());
        res.setCategory(entity.getCategory());
        res.setTenderMethod(entity.getTenderMethod());
        res.setDeadline(entity.getDeadline());
        res.setOpeningTime(entity.getOpeningTime());
        res.setOpeningLocation(entity.getOpeningLocation());
        res.setContactName(entity.getContactName());
        res.setContactPhone(entity.getContactPhone());
        res.setBudgetAmount(entity.getBudgetAmount());
        res.setBidBondRequired(entity.getBidBondRequired());
        res.setPerformanceLocation(entity.getPerformanceLocation());
        res.setSourceUrl(entity.getSourceUrl());
        res.setCreatedAt(entity.getCreatedAt());

        return res;
    }
}
