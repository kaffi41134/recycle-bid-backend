package com.cathy.recycle_bid_backend.function.material;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.cathy.recycle_bid_backend.function.material.dto.MaterialReq;
import com.cathy.recycle_bid_backend.function.material.dto.MaterialRes;

// 專用記憶體資料庫；測試不會連到開發或正式資料庫。
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:material-timestamp-tests",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional
class MaterialPersistenceTests {
    @Autowired
    private MaterialService service;

    @Test
    void createsTimestampAndPreservesItOnUpdate() {
        MaterialReq request = new MaterialReq();
        request.setName("建立時間測試品項");
        request.setCategory("金屬類");
        request.setUnit("KG");
        request.setEnabled(true);

        MaterialRes created = service.create(request);
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getCode());

        request.setName("更新後的測試品項");
        service.update(created.getId(), request);
        MaterialRes updated = service.findById(created.getId());
        assertEquals(created.getCreatedAt(), updated.getCreatedAt());
        assertEquals("更新後的測試品項", updated.getName());
    }
}
