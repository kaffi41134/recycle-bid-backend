package com.cathy.recycle_bid_backend.function.material.dto;

import java.time.LocalDateTime;

import com.cathy.recycle_bid_backend.data.entity.Material;

import lombok.Data;

@Data
public class MaterialRes {

    private Long id;
    private String name;
    private String category;
    private String code;
    private String unit;
    private Boolean enabled;
    private LocalDateTime createdAt;

    public static MaterialRes of(Material entity) {
        MaterialRes res = new MaterialRes();
        res.setId(entity.getId());
        res.setName(entity.getName());
        res.setCategory(entity.getCategory());
        res.setCode(entity.getCode());
        res.setUnit(entity.getUnit());
        res.setEnabled(entity.getEnabled());
        res.setCreatedAt(entity.getCreatedAt());
        return res;
    }

}
