package com.cathy.recycle_bid_backend.data.entity;

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

    // 案件編號
    private String bidNo;

    // 案件名稱
    private String title;

    // 招標公司
    private String companyName;

    // 聯絡人
    private String contactName;

    // 聯絡電話
    private String contactPhone;

    // 公告日期
    private LocalDate publishDate;

    // 投標截止日期
    private LocalDate deadlineDate;

    // 案件狀態，預設為準備中
    @Builder.Default
    private String status = "DRAFT";

    // 備註
    private String note;

     // 建立時間
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
}
