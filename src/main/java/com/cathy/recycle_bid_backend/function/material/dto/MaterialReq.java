package com.cathy.recycle_bid_backend.function.material.dto;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.cathy.recycle_bid_backend.data.entity.Material;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaterialReq {

    @NotBlank(message = "品項名稱 不得空白!!")
    private String name;

    @NotBlank(message = "分類 不得空白!!")
    private String category;

    @NotBlank(message = "單位 不得空白!!")
    private String unit;

    private Boolean enabled;

    public Material toEntity() {
        return Material.builder()
                .name(StringUtils.trim(name))
                .category(StringUtils.trim(category))
                .unit(StringUtils.trim(unit))
                .enabled(Objects.requireNonNullElse(enabled, true))
                .build();
    }

}
