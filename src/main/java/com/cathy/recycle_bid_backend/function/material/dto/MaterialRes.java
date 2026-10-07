package com.cathy.recycle_bid_backend.function.material.dto;

import com.cathy.recycle_bid_backend.data.entity.Material;

import lombok.Data;

@Data
public class MaterialRes {

    private Long id;
    private String code;
    private String name;
    private String category;
    private String unit;
    private Boolean enabled;

    public static MaterialRes of(Material entity) {
        MaterialRes res = new MaterialRes();
        res.setId(entity.getId());
        res.setCode(entity.getCode());
        res.setName(entity.getName());
        res.setCategory(entity.getCategory());
        res.setUnit(entity.getUnit());
        res.setEnabled(entity.getEnabled());
        return res;
    }

}
