package com.cathy.recycle_bid_backend.function.bid.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

import com.cathy.recycle_bid_backend.data.entity.Bid;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BidReq {

    @NotBlank(message = "標售案號不得空白")
    private String bidNo;

    private String contactName;

    private String contactPhone;

    private String materialCode;

    private String itemDescription;

    private BigDecimal quantity;

    private String unit;

    private String pickupLocation;

    private LocalDate quotationNoticeDate;

    private LocalDate quotationDeadlineDate;

    private String environmentalCode;

    private String referenceStorageLocation;

    @AssertTrue(message = "報價截止日不能早於報價通知日")
    public boolean isDateRangeValid() {
        if (quotationNoticeDate == null || quotationDeadlineDate == null) {
            return true;
        }

        return !quotationDeadlineDate.isBefore(quotationNoticeDate);
    }

    // 新增：建立新的 Entity
    public Bid toEntity() {
        return Bid.builder()
                .bidNo(StringUtils.trimToNull(bidNo))
                .contactName(StringUtils.trimToNull(contactName))
                .contactPhone(StringUtils.trimToNull(contactPhone))
                .materialCode(StringUtils.trimToNull(materialCode))
                .itemDescription(StringUtils.trimToNull(itemDescription))
                .quantity(quantity)
                .unit(StringUtils.trimToNull(unit))
                .pickupLocation(StringUtils.trimToNull(pickupLocation))
                .quotationNoticeDate(quotationNoticeDate)
                .quotationDeadlineDate(quotationDeadlineDate)
                .environmentalCode(StringUtils.trimToNull(environmentalCode))
                .referenceStorageLocation(StringUtils.trimToNull(referenceStorageLocation))
                .build();
    }

    // 修改：更新既有 Entity
    public Bid applyTo(Bid entity) {
        entity.setBidNo(StringUtils.trimToNull(bidNo));
        entity.setContactName(StringUtils.trimToNull(contactName));
        entity.setContactPhone(StringUtils.trimToNull(contactPhone));
        entity.setMaterialCode(StringUtils.trimToNull(materialCode));
        entity.setItemDescription(StringUtils.trimToNull(itemDescription));
        entity.setQuantity(quantity);
        entity.setUnit(StringUtils.trimToNull(unit));
        entity.setPickupLocation(StringUtils.trimToNull(pickupLocation));
        entity.setQuotationNoticeDate(quotationNoticeDate);
        entity.setQuotationDeadlineDate(quotationDeadlineDate);
        entity.setEnvironmentalCode(StringUtils.trimToNull(environmentalCode));
        entity.setReferenceStorageLocation(StringUtils.trimToNull(referenceStorageLocation));

        return entity;
    }
}
