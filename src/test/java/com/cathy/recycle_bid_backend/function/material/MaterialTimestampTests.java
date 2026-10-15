package com.cathy.recycle_bid_backend.function.material;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.cathy.recycle_bid_backend.data.entity.Material;
import com.cathy.recycle_bid_backend.function.material.dto.MaterialReq;
import com.cathy.recycle_bid_backend.function.material.dto.MaterialRes;

class MaterialTimestampTests {
    @Test
    void updatePreservesCreationTimeAndResponseIncludesIt() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 10, 14, 16, 30);
        Material entity = Material.builder().id(1L).createdAt(createdAt).build();
        MaterialReq request = new MaterialReq();
        request.setName("廢鐵");
        request.setCategory("金屬類");
        request.setUnit("KG");
        request.setEnabled(false);

        request.applyto(entity);

        assertEquals(createdAt, entity.getCreatedAt());
        assertEquals(createdAt, MaterialRes.of(entity).getCreatedAt());
    }
}
