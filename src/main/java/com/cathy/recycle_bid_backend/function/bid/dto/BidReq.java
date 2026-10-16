package com.cathy.recycle_bid_backend.function.bid.dto;

import java.time.LocalDate;

import com.cathy.recycle_bid_backend.data.entity.Bid;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import org.apache.commons.lang3.StringUtils;

@Data
public class BidReq {

    @NotBlank(message = "案件名稱不得空白")
    private String title;

    @NotBlank(message = "招標公司不得空白")
    private String companyName;

    private String contactName;

    private String contactPhone;

    private LocalDate publishDate;

    private LocalDate deadlineDate;

    @NotBlank(message = "案件狀態不得空白")
    private String status;

    private String note;

    // 新增：建立新的 Entity
    public Bid toEntity() {
        return Bid.builder()
                .title(StringUtils.trim(title))
                .companyName(StringUtils.trim(companyName))
                .contactName(StringUtils.trimToNull(contactName))
                .contactPhone(StringUtils.trimToNull(contactPhone))
                .publishDate(publishDate)
                .deadlineDate(deadlineDate)
                .status(status)
                .note(StringUtils.trimToNull(note))
                .build();
    }

    // 修改：更新既有 Entity
    public Bid applyTo(Bid entity) {
        entity.setTitle(StringUtils.trim(title));
        entity.setCompanyName(StringUtils.trim(companyName));
        entity.setContactName(StringUtils.trimToNull(contactName));
        entity.setContactPhone(StringUtils.trimToNull(contactPhone));
        entity.setPublishDate(publishDate);
        entity.setDeadlineDate(deadlineDate);
        entity.setStatus(status);
        entity.setNote(StringUtils.trimToNull(note));

        return entity;
    }

}
