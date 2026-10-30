package com.cathy.recycle_bid_backend.function.bid.formosa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cathy.recycle_bid_backend.data.entity.Bid;

import lombok.Data;

@Data
public class BidRes {

    private Long id;
    private String bidNo;
    private String contactName;
    private String contactPhone;
    private String materialCode;
    private String itemDescription;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal awardAmount;
    private LocalDate awardDate;
    private String pickupLocation;
    private LocalDate quotationNoticeDate;
    private LocalDate quotationDeadlineDate;
    private String environmentalCode;
    private String referenceStorageLocation;
    private LocalDateTime createdAt;

    // 將 Entity 轉成 API 回應資料
    public static BidRes of(Bid entity) {
        BidRes res = new BidRes();
        res.setId(entity.getId());
        res.setBidNo(entity.getBidNo());
        res.setContactName(entity.getContactName());
        res.setContactPhone(entity.getContactPhone());
        res.setMaterialCode(entity.getMaterialCode());
        res.setItemDescription(entity.getItemDescription());
        res.setQuantity(entity.getQuantity());
        res.setUnit(entity.getUnit());
        res.setAwardAmount(entity.getAwardAmount());
        res.setAwardDate(entity.getAwardDate());
        res.setPickupLocation(entity.getPickupLocation());
        res.setQuotationNoticeDate(entity.getQuotationNoticeDate());
        res.setQuotationDeadlineDate(entity.getQuotationDeadlineDate());
        res.setEnvironmentalCode(entity.getEnvironmentalCode());
        res.setReferenceStorageLocation(entity.getReferenceStorageLocation());
        res.setCreatedAt(entity.getCreatedAt());

        return res;
    }
}
