package com.cathy.recycle_bid_backend.data.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BIDS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 標售案號
    private String bidNo;

    // 聯絡人
    private String contactName;

    // 電話
    private String contactPhone;

    // 材料編號
    private String materialCode;

    // 品名規格
    private String itemDescription;

    // 數量
    @Column(precision = 19, scale = 6)
    private BigDecimal quantity;

    // 單位
    private String unit;

    // 決標金額
    @Column(precision = 19, scale = 2)
    private BigDecimal awardAmount;

    // 決標日
    private LocalDate awardDate;

    // 提貨地點
    private String pickupLocation;

    // 報價通知日
    private LocalDate quotationNoticeDate;

    // 報價截止日
    private LocalDate quotationDeadlineDate;

    // 環保法定代碼
    private String environmentalCode;

    // 參考料位
    private String referenceStorageLocation;

    // 建立時間
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
}
