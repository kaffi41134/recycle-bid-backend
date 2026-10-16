package com.cathy.recycle_bid_backend.function.bid.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cathy.recycle_bid_backend.data.entity.Bid;

import lombok.Data;

@Data
public class BidRes {

    private Long id;
    private String bidNo;
    private String title;
    private String companyName;
    private String contactName;
    private String contactPhone;
    private LocalDate publishDate;
    private LocalDate deadlineDate;
    private String status;
    private String note;
    private LocalDateTime createdAt;

    // 將 Entity 轉成 API 回應資料
    public static BidRes of(Bid entity) {
        BidRes res = new BidRes();
        res.setId(entity.getId());
        res.setBidNo(entity.getBidNo());
        res.setTitle(entity.getTitle());
        res.setCompanyName(entity.getCompanyName());
        res.setContactName(entity.getContactName());
        res.setContactPhone(entity.getContactPhone());
        res.setPublishDate(entity.getPublishDate());
        res.setDeadlineDate(entity.getDeadlineDate());
        res.setStatus(entity.getStatus());
        res.setNote(entity.getNote());
        res.setCreatedAt(entity.getCreatedAt());

        return res;
    }
    
}
