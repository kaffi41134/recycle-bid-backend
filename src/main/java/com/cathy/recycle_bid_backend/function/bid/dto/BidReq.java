package com.cathy.recycle_bid_backend.function.bid.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

import com.cathy.recycle_bid_backend.data.entity.Bid;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;

@Data
public class BidReq {

    @NotBlank(message = "標售案號不得空白")
    private String bidNo;

    private String contactName;

    private String contactPhone;

    private String materialCode;

    private String itemDescription;

    @DecimalMin(value = "0", message = "數量不得小於零")
    @Digits(integer = 13, fraction = 6, message = "數量最多 13 位整數及 6 位小數")
    private BigDecimal quantity;

    private String unit;

    @DecimalMin(value = "0", message = "決標金額不得小於零")
    @Digits(integer = 17, fraction = 2, message = "決標金額最多 17 位整數及 2 位小數")
    private BigDecimal awardAmount;

    private LocalDate awardDate;

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
                .awardAmount(awardAmount)
                .awardDate(awardDate)
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
        entity.setAwardAmount(awardAmount);
        entity.setAwardDate(awardDate);
        entity.setPickupLocation(StringUtils.trimToNull(pickupLocation));
        entity.setQuotationNoticeDate(quotationNoticeDate);
        entity.setQuotationDeadlineDate(quotationDeadlineDate);
        entity.setEnvironmentalCode(StringUtils.trimToNull(environmentalCode));
        entity.setReferenceStorageLocation(StringUtils.trimToNull(referenceStorageLocation));

        return entity;
    }
}
