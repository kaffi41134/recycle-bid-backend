package com.cathy.recycle_bid_backend.function.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
public class AuthReq {

    @NotBlank(message = "帳號不得空白")
    private String username;

    @ToString.Exclude
    @NotBlank(message = "密碼不得空白")
    private String password;
}
