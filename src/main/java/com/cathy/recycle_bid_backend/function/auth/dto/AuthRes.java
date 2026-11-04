package com.cathy.recycle_bid_backend.function.auth.dto;

import java.time.Instant;

import lombok.Data;
import lombok.ToString;

@Data
public class AuthRes {

    // 登入憑證
    @ToString.Exclude
    private String accessToken;

    // 憑證類型
    private String tokenType;

    // 到期時間
    private Instant expiresAt;

    public static AuthRes of(String accessToken, Instant expiresAt) {
        AuthRes res = new AuthRes();
        res.setAccessToken(accessToken);
        res.setTokenType("Bearer");
        res.setExpiresAt(expiresAt);
        return res;
    }
}
