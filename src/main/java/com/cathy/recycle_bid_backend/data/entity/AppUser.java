package com.cathy.recycle_bid_backend.data.entity;

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
import lombok.ToString;

@Entity
@Table(name = "APP_USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 登入帳號
    @Column(unique = true)
    private String username;

    // 密碼雜湊值
    @ToString.Exclude
    private String passwordHash;

    // 是否啟用，預設為 true
    @Builder.Default
    private boolean enabled = true;
}