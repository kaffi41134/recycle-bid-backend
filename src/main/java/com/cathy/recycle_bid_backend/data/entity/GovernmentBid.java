package com.cathy.recycle_bid_backend.data.entity;

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
@Table(name = "GOVERNMENT_BIDS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GovernmentBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 招標機關代碼
    private String unitId;

    // 標案案號
    private String jobNumber;

    // 招標機關名稱
    private String unitName;

    // 標案名稱
    private String title;

    // 公告日期
    private String noticeDate;

    // 公告類型
    private String noticeType;

    // 標的分類
    private String category;

    // 招標方式
    private String tenderMethod;

    // 截止投標時間
    private String deadline;

    // 開標時間
    private String openingTime;

    // 開標地點
    private String openingLocation;

    // 機關聯絡人
    private String contactName;

    // 機關聯絡電話
    private String contactPhone;

    // 預算金額
    private String budgetAmount;

    // 是否須繳納押標金
    private String bidBondRequired;

    // 履約地點
    private String performanceLocation;

    // 政府標案公告網址
    private String sourceUrl;

    // 本系統建立案件的時間
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
